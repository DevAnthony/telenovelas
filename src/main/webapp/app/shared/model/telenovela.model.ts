import { IEpisode } from 'app/shared/model/episode.model';
import { IActor } from 'app/shared/model/actor.model';

export interface ITelenovela {
  id?: number;
  title?: string;
  summary?: any;
  rating?: number;
  year?: number;
  country?: string;
  playlist?: string;
  thumbnail?: string;
  poster?: string;
  background?: string;
  episodes?: IEpisode[];
  actors?: IActor[];
}

export const defaultValue: Readonly<ITelenovela> = {};
