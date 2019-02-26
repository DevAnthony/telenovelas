import { ITelenovela } from 'app/shared/model/telenovela.model';
import { IEpisode } from 'app/shared/model/episode.model';

export interface IActor {
  id?: number;
  firstname?: string;
  middlename?: string;
  lastname?: string;
  profilepicture?: string;
  biolink?: string;
  telenovelas?: ITelenovela[];
  episodes?: IEpisode[];
}

export const defaultValue: Readonly<IActor> = {};
