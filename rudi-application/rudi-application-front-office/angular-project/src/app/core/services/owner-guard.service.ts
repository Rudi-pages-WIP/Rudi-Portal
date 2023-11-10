import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {ProjektService} from '../../projekt/projekt-api';
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class OwnerGuardService implements CanActivate, CanActivateChild {

    // Gestion d'un loader durant l'authentification
    public loader = false;

    constructor(
        private readonly projectService: ProjektService,
        private readonly router: Router,) {
    }

    /**
     * Accès aux urls de même niveau
     * Renvoie true si utilisateur a le droit et false pas le droit
     * @returns Observable<any>
     */
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        // récupération du l'UUID du projet dans l'url
        const routeSegement = state.url.split('/');
        const uuid: string = routeSegement[routeSegement.length - 1];

        return this.projectService.isAuthenticatedUserProjectOwner(uuid)
            .pipe(
                map((isOwner: boolean) => {
                        if (isOwner) {
                            return isOwner;
                        }
                        this.router.navigate(['/not-authorized']);
                        return false;
                    }
                )
            );
    }

    /**
     * Accès aux urls enfants même traitement que les url parents
     * @returns Observable<any>
     */
    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.canActivate(childRoute, state);
    }

}
