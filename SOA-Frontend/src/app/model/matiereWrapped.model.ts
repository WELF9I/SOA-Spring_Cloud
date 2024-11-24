import { Matiere } from './matiere.model';

export class MatiereWrapper {
    _embedded!: { matieres: Matiere[] }; 
}
