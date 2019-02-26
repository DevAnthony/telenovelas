import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEpisode, defaultValue } from 'app/shared/model/episode.model';

export const ACTION_TYPES = {
  FETCH_EPISODE_LIST: 'episode/FETCH_EPISODE_LIST',
  FETCH_EPISODE: 'episode/FETCH_EPISODE',
  CREATE_EPISODE: 'episode/CREATE_EPISODE',
  UPDATE_EPISODE: 'episode/UPDATE_EPISODE',
  DELETE_EPISODE: 'episode/DELETE_EPISODE',
  RESET: 'episode/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEpisode>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EpisodeState = Readonly<typeof initialState>;

// Reducer

export default (state: EpisodeState = initialState, action): EpisodeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EPISODE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EPISODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EPISODE):
    case REQUEST(ACTION_TYPES.UPDATE_EPISODE):
    case REQUEST(ACTION_TYPES.DELETE_EPISODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EPISODE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EPISODE):
    case FAILURE(ACTION_TYPES.CREATE_EPISODE):
    case FAILURE(ACTION_TYPES.UPDATE_EPISODE):
    case FAILURE(ACTION_TYPES.DELETE_EPISODE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EPISODE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_EPISODE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EPISODE):
    case SUCCESS(ACTION_TYPES.UPDATE_EPISODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EPISODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/episodes';

// Actions

export const getEntities: ICrudGetAllAction<IEpisode> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EPISODE_LIST,
    payload: axios.get<IEpisode>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEpisode> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EPISODE,
    payload: axios.get<IEpisode>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEpisode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EPISODE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IEpisode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EPISODE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEpisode> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EPISODE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
