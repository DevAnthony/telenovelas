import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITelenovela } from 'app/shared/model/telenovela.model';
import { getEntities as getTelenovelas } from 'app/entities/telenovela/telenovela.reducer';
import { IEpisode } from 'app/shared/model/episode.model';
import { getEntities as getEpisodes } from 'app/entities/episode/episode.reducer';
import { getEntity, updateEntity, createEntity, reset } from './actor.reducer';
import { IActor } from 'app/shared/model/actor.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IActorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IActorUpdateState {
  isNew: boolean;
  telenovelaId: string;
  episodeId: string;
}

export class ActorUpdate extends React.Component<IActorUpdateProps, IActorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      telenovelaId: '0',
      episodeId: '0',
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

    this.props.getTelenovelas();
    this.props.getEpisodes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { actorEntity } = this.props;
      const entity = {
        ...actorEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/actor');
  };

  render() {
    const { actorEntity, telenovelas, episodes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="novelasApp.actor.home.createOrEditLabel">
              <Translate contentKey="novelasApp.actor.home.createOrEditLabel">Create or edit a Actor</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : actorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="actor-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="firstnameLabel" for="firstname">
                    <Translate contentKey="novelasApp.actor.firstname">Firstname</Translate>
                  </Label>
                  <AvField
                    id="actor-firstname"
                    type="text"
                    name="firstname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="middlenameLabel" for="middlename">
                    <Translate contentKey="novelasApp.actor.middlename">Middlename</Translate>
                  </Label>
                  <AvField id="actor-middlename" type="text" name="middlename" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastnameLabel" for="lastname">
                    <Translate contentKey="novelasApp.actor.lastname">Lastname</Translate>
                  </Label>
                  <AvField
                    id="actor-lastname"
                    type="text"
                    name="lastname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="profilepictureLabel" for="profilepicture">
                    <Translate contentKey="novelasApp.actor.profilepicture">Profilepicture</Translate>
                  </Label>
                  <AvField
                    id="actor-profilepicture"
                    type="text"
                    name="profilepicture"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="biolinkLabel" for="biolink">
                    <Translate contentKey="novelasApp.actor.biolink">Biolink</Translate>
                  </Label>
                  <AvField
                    id="actor-biolink"
                    type="text"
                    name="biolink"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/actor" replace color="info">
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
  telenovelas: storeState.telenovela.entities,
  episodes: storeState.episode.entities,
  actorEntity: storeState.actor.entity,
  loading: storeState.actor.loading,
  updating: storeState.actor.updating,
  updateSuccess: storeState.actor.updateSuccess
});

const mapDispatchToProps = {
  getTelenovelas,
  getEpisodes,
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
)(ActorUpdate);
