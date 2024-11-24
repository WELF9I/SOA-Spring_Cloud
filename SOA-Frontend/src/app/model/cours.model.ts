import { Matiere } from "./matiere.model";
import { Image } from "./image.model";
export class Cours {
    id!: number;
    title!: string;
    content!: string;
    duration!: number;
    createdDate!: string;
    videoUrl!: string;
    matiere!: Matiere; 
    image! : Image;
    imageStr!:string;
    images!: Image[];
}
