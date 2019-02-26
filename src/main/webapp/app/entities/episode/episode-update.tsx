import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { ITelenovela } from 'app/shared/model/telenovela.model';
import { getEntities as getTelenovelas } from 'app/entities/telenovela/telenovela.reducer';
import { getEntity, updateEntity, createEntity, reset } from './episode.reducer';
import { IEpisode } from 'app/shared/model/episode.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEpisodeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEpisodeUpdateState {
  isNew: boolean;
  idsactor: any[];
  telenovelaId: string;
}

export class EpisodeUpdate extends React.Component<IEpisodeUpdateProps, IEpisodeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsactor: [],
      telenovelaId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getActors();
    this.props.getTelenovelas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { episodeEntity } = this.props;
      const entity = {
        ...episodeEntity,
        ...values,
        actors: mapIdList(values.actors)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/episode');
  };

  render() {
    const { episodeEntity, actors, telenovelas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="novelasApp.episode.home.createOrEditLabel">
              <Translate contentKey="novelasApp.episode.home.createOrEditLabel">Create or edit a Episode</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : episodeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="episode-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="numberLabel" for="number">
                    <Translate contentKey="novelasApp.episode.number">Number</Translate>
                  </Label>
                  <AvField
                    id="episode-number"
                    type="string"
                    className="form-control"
                    name="number"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    <Translate contentKey="novelasApp.episode.title">Title</Translate>
                  </Label>
                  <AvField id="episode-title" type="text" name="title" />
                </AvGroup>
                <AvGroup>
                  <Label id="ratingLabel" for="rating">
                    <Translate contentKey="novelasApp.episode.rating">Rating</Translate>
                  </Label>
                  <AvField id="episode-rating" type="string" className="form-control" name="rating" />
                </AvGroup>
                <AvGroup>
                  <Label id="downloadlinkLabel" for="downloadlink">
                    <Translate contentKey="novelasApp.episode.downloadlink">Downloadlink</Translate>
                  </Label>
                  <AvField
                    id="episode-downloadlink"
                    type="text"
                    name="downloadlink"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="streaminglinkLabel" for="streaminglink">
                    <Translate contentKey="novelasApp.episode.streaminglink">Streaminglink</Translate>
                  </Label>
                  <AvField
                    id="episode-streaminglink"
                    type="text"
                    name="streaminglink"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="actors">
                    <Translate contentKey="novelasApp.episode.actor">Actor</Translate>
                  </Label>
                  <AvInput
                    id="episode-actor"
                    type="select"
                    multiple
                    className="form-control"
                    name="actors"
                    value={episodeEntity.actors && episodeEntity.actors.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {actors
                      ? actors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="telenovela.id">
                    <Translate contentKey="novelasApp.episode.telenovela">Telenovela</Translate>
                  </Label>
                  <AvInput id="episode-telenovela" type="select" className="form-control" name="telenovelaId">
                    <option value="" key="0" />
                    {telenovelas
                      ? telenovelas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/episode" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  actors: storeState.actor.entities,
  telenovelas: storeState.telenovela.entities,
  episodeEntity: storeState.episode.entity,
  loading: storeState.episode.loading,
  updating: storeState.episode.updating,
  updateSuccess: storeState.episode.updateSuccess
});

const mapDispatchToProps = {
  getActors,
  getTelenovelas,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EpisodeUpdate);
