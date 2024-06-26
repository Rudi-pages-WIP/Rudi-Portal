import {Injectable} from '@angular/core';
import {MatIconRegistry} from '@angular/material/icon';
import {DomSanitizer} from '@angular/platform-browser';
import {TitleIconType} from '@shared/models/title-icon-type';

@Injectable({
    providedIn: 'root'
})
export class IconRegistryService {

    constructor(
        private readonly matIconRegistry: MatIconRegistry,
        private readonly domSanitizer: DomSanitizer,
    ) {
    }

    addAllSvgIcons(types: TitleIconType[]): void {
        types.forEach(type => this.addSvgIcon(type));
    }

    addSvgIcon(type: TitleIconType): void {
        this.matIconRegistry.addSvgIcon(
            type,
            this.domSanitizer.bypassSecurityTrustResourceUrl(`/assets/icons/${type}.svg`)
        );
    }
}
