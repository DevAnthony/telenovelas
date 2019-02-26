import { IActor } from 'app/shared/model/actor.model';

export interface IEpisode {
  id?: number;
  number?: number;
  title?: string;
  rating?: number;
  downloadlink?: string;
  streaminglink?: string;
  actors?: IActor[];
  telenovelaId?: number;
}

export const defaultValue: Readonly<IEpisode> = {};
