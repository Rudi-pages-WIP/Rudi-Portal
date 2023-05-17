package org.rudi.microservice.projekt.service.ownerinfo.impl;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.rudi.common.service.exception.AppServiceBadRequestException;
import org.rudi.common.service.exception.AppServiceException;
import org.rudi.common.service.exception.AppServiceNotFoundException;
import org.rudi.microservice.projekt.core.bean.LinkedDataset;
import org.rudi.microservice.projekt.core.bean.LinkedDatasetSearchCriteria;
import org.rudi.microservice.projekt.core.bean.LinkedDatasetStatus;
import org.rudi.microservice.projekt.core.bean.OwnerInfo;
import org.rudi.microservice.projekt.core.bean.OwnerType;
import org.rudi.microservice.projekt.service.ownerinfo.OwnerInfoService;
import org.rudi.microservice.projekt.storage.dao.linkeddataset.LinkedDatasetCustomDao;
import org.rudi.microservice.projekt.storage.dao.linkeddataset.LinkedDatasetDao;
import org.rudi.microservice.projekt.storage.dao.project.ProjectCustomDao;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerInfoServiceImpl implements OwnerInfoService {

	private final List<OwnerInfoHelper> ownerInfoHelpers;
	private final EnumMap<OwnerType, OwnerInfoHelper> ownerInfoHelpersByOwnerType = new EnumMap<>(OwnerType.class);
	private final LinkedDatasetCustomDao linkedDatasetCustomDao;
	private final LinkedDatasetDao linkedDatasetDao;
	private final ProjectCustomDao projectCustomDao;

	@Override
	public OwnerInfo getOwnerInfo(OwnerType ownerType, UUID ownerUuid) throws AppServiceException {
		OwnerInfoHelper helper = findHelperFor(ownerType);
		return helper.getOwnerInfo(ownerUuid);
	}

	@Nonnull
	private OwnerInfoHelper findHelperFor(OwnerType ownerType) {
		final var ownerInfoHelper = ownerInfoHelpersByOwnerType.computeIfAbsent(ownerType, k -> ownerInfoHelpers.stream()
				.filter(helper -> helper.isHelperFor(ownerType))
				.findFirst()
				.orElse(null));
		if (ownerInfoHelper == null) {
			throw new OwnerInfoHelperNotImplementedException(ownerType);
		}
		return ownerInfoHelper;
	}

	@Override
	public boolean hasAccessToDataset(UUID uuidToCheck, UUID datasetUuid) throws AppServiceBadRequestException {
		if (uuidToCheck == null) {
			throw new AppServiceBadRequestException("L'UUID du owner est obligatoire");
		}
		if (datasetUuid == null) {
			throw new AppServiceBadRequestException("L'UUID du dataset est obligatoire.");
		}

		var linkedDatasetSearchCriteria = new LinkedDatasetSearchCriteria()
				.datasetUuid(datasetUuid)
				.projectOwnerUuids(List.of(uuidToCheck))
				.status(List.of(LinkedDatasetStatus.VALIDATED))
				.checkEndDate(true);
		final var linkedDatasetEntities = linkedDatasetCustomDao.searchLinkedDatasets(linkedDatasetSearchCriteria, Pageable.unpaged());
		return linkedDatasetEntities.getTotalElements() > 0;
	}

	@Override
	public UUID getLinkedDatasetOwner(UUID linkedDatasetUuid) throws AppServiceNotFoundException {
		final var linkedDatasetEntity = linkedDatasetDao.findByUuid(linkedDatasetUuid);
		if (linkedDatasetEntity == null) {
			throw new AppServiceNotFoundException(LinkedDataset.class, linkedDatasetUuid);
		}

		return projectCustomDao.findProjectByLinkedDatasetUuid(linkedDatasetUuid).getOwnerUuid();
	}
}
