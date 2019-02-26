import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './actor.reducer';
import { IActor } from 'app/shared/model/actor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IActorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ActorDetail extends React.Component<IActorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { actorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="novelasApp.actor.detail.title">Actor</Translate> [<b>{actorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstname">
                <Translate contentKey="novelasApp.actor.firstname">Firstname</Translate>
              </span>
            </dt>
            <dd>{actorEntity.firstname}</dd>
            <dt>
              <span id="middlename">
                <Translate contentKey="novelasApp.actor.middlename">Middlename</Translate>
              </span>
            </dt>
            <dd>{actorEntity.middlename}</dd>
            <dt>
              <span id="lastname">
                <Translate contentKey="novelasApp.actor.lastname">Lastname</Translate>
              </span>
            </dt>
            <dd>{actorEntity.lastname}</dd>
            <dt>
              <span id="profilepicture">
                <Translate contentKey="novelasApp.actor.profilepicture">Profilepicture</Translate>
              </span>
            </dt>
            <dd>{actorEntity.profilepicture}</dd>
            <dt>
              <span id="biolink">
                <Translate contentKey="novelasApp.actor.biolink">Biolink</Translate>
              </span>
            </dt>
            <dd>{actorEntity.biolink}</dd>
          </dl>
          <Button tag={Link} to="/entity/actor" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/actor/${actorEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ actor }: IRootState) => ({
  actorEntity: actor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActorDetail);
