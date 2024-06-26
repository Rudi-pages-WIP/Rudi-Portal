import {Injectable} from '@angular/core';
import {FrontOfficeProperties, KonsultService} from 'micro_service_modules/konsult/konsult-api';
import {Observable} from 'rxjs';
import {PropertiesAdapter} from './properties-adapter';

@Injectable({
    providedIn: 'root'
})
export class PropertiesMetierService extends PropertiesAdapter<FrontOfficeProperties> {

    constructor(
        private readonly konsultService: KonsultService,
    ) {
        super();
    }

    protected fetchBackendProperties(): Observable<FrontOfficeProperties> {
        return this.konsultService.getFrontOfficeProperties();
    }
}
