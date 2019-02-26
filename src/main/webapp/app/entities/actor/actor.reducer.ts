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

import { IActor, defaultValue } from 'app/shared/model/actor.model';

export const ACTION_TYPES = {
  FETCH_ACTOR_LIST: 'actor/FETCH_ACTOR_LIST',
  FETCH_ACTOR: 'actor/FETCH_ACTOR',
  CREATE_ACTOR: 'actor/CREATE_ACTOR',
  UPDATE_ACTOR: 'actor/UPDATE_ACTOR',
  DELETE_ACTOR: 'actor/DELETE_ACTOR',
  RESET: 'actor/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IActor>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ActorState = Readonly<typeof initialState>;

// Reducer

export default (state: ActorState = initialState, action): ActorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACTOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACTOR):
    case REQUEST(ACTION_TYPES.UPDATE_ACTOR):
    case REQUEST(ACTION_TYPES.DELETE_ACTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACTOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACTOR):
    case FAILURE(ACTION_TYPES.CREATE_ACTOR):
    case FAILURE(ACTION_TYPES.UPDATE_ACTOR):
    case FAILURE(ACTION_TYPES.DELETE_ACTOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTOR_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACTOR):
    case SUCCESS(ACTION_TYPES.UPDATE_ACTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACTOR):
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

const apiUrl = 'api/actors';

// Actions

export const getEntities: ICrudGetAllAction<IActor> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACTOR_LIST,
    payload: axios.get<IActor>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IActor> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACTOR,
    payload: axios.get<IActor>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IActor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACTOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IActor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACTOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IActor> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACTOR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
