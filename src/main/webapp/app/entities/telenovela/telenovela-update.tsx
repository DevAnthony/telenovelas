import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './telenovela.reducer';
import { ITelenovela } from 'app/shared/model/telenovela.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITelenovelaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITelenovelaUpdateState {
  isNew: boolean;
  idsactor: any[];
}

export class TelenovelaUpdate extends React.Component<ITelenovelaUpdateProps, ITelenovelaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsactor: [],
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
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { telenovelaEntity } = this.props;
      const entity = {
        ...telenovelaEntity,
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
    this.props.history.push('/entity/telenovela');
  };

  render() {
    const { telenovelaEntity, actors, loading, updating } = this.props;
    const { isNew } = this.state;

    const { summary } = telenovelaEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="novelasApp.telenovela.home.createOrEditLabel">
              <Translate contentKey="novelasApp.telenovela.home.createOrEditLabel">Create or edit a Telenovela</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : telenovelaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="telenovela-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    <Translate contentKey="novelasApp.telenovela.title">Title</Translate>
                  </Label>
                  <AvField
                    id="telenovela-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="summaryLabel" for="summary">
                    <Translate contentKey="novelasApp.telenovela.summary">Summary</Translate>
                  </Label>
                  <AvInput id="telenovela-summary" type="textarea" name="summary" />
                </AvGroup>
                <AvGroup>
                  <Label id="ratingLabel" for="rating">
                    <Translate contentKey="novelasApp.telenovela.rating">Rating</Translate>
                  </Label>
                  <AvField id="telenovela-rating" type="string" className="form-control" name="rating" />
                </AvGroup>
                <AvGroup>
                  <Label id="yearLabel" for="year">
                    <Translate contentKey="novelasApp.telenovela.year">Year</Translate>
                  </Label>
                  <AvField id="telenovela-year" type="string" className="form-control" name="year" />
                </AvGroup>
                <AvGroup>
                  <Label id="countryLabel" for="country">
                    <Translate contentKey="novelasApp.telenovela.country">Country</Translate>
                  </Label>
                  <AvField
                    id="telenovela-country"
                    type="text"
                    name="country"
                    validate={{
                      maxLength: { value: 15, errorMessage: translate('entity.validation.maxlength', { max: 15 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="playlistLabel" for="playlist">
                    <Translate contentKey="novelasApp.telenovela.playlist">Playlist</Translate>
                  </Label>
                  <AvField
                    id="telenovela-playlist"
                    type="text"
                    name="playlist"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="thumbnailLabel" for="thumbnail">
                    <Translate contentKey="novelasApp.telenovela.thumbnail">Thumbnail</Translate>
                  </Label>
                  <AvField
                    id="telenovela-thumbnail"
                    type="text"
                    name="thumbnail"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="posterLabel" for="poster">
                    <Translate contentKey="novelasApp.telenovela.poster">Poster</Translate>
                  </Label>
                  <AvField
                    id="telenovela-poster"
                    type="text"
                    name="poster"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="backgroundLabel" for="background">
                    <Translate contentKey="novelasApp.telenovela.background">Background</Translate>
                  </Label>
                  <AvField
                    id="telenovela-background"
                    type="text"
                    name="background"
                    validate={{
                      maxLength: { value: 2048, errorMessage: translate('entity.validation.maxlength', { max: 2048 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="actors">
                    <Translate contentKey="novelasApp.telenovela.actor">Actor</Translate>
                  </Label>
                  <AvInput
                    id="telenovela-actor"
                    type="select"
                    multiple
                    className="form-control"
                    name="actors"
                    value={telenovelaEntity.actors && telenovelaEntity.actors.map(e => e.id)}
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
                <Button tag={Link} id="cancel-save" to="/entity/telenovela" replace color="info">
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
  telenovelaEntity: storeState.telenovela.entity,
  loading: storeState.telenovela.loading,
  updating: storeState.telenovela.updating,
  updateSuccess: storeState.telenovela.updateSuccess
});

const mapDispatchToProps = {
  getActors,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TelenovelaUpdate);
