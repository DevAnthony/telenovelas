import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './telenovela.reducer';
import { ITelenovela } from 'app/shared/model/telenovela.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITelenovelaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TelenovelaDetail extends React.Component<ITelenovelaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { telenovelaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="novelasApp.telenovela.detail.title">Telenovela</Translate> [<b>{telenovelaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">
                <Translate contentKey="novelasApp.telenovela.title">Title</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.title}</dd>
            <dt>
              <span id="summary">
                <Translate contentKey="novelasApp.telenovela.summary">Summary</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.summary}</dd>
            <dt>
              <span id="rating">
                <Translate contentKey="novelasApp.telenovela.rating">Rating</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.rating}</dd>
            <dt>
              <span id="year">
                <Translate contentKey="novelasApp.telenovela.year">Year</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.year}</dd>
            <dt>
              <span id="country">
                <Translate contentKey="novelasApp.telenovela.country">Country</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.country}</dd>
            <dt>
              <span id="playlist">
                <Translate contentKey="novelasApp.telenovela.playlist">Playlist</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.playlist}</dd>
            <dt>
              <span id="thumbnail">
                <Translate contentKey="novelasApp.telenovela.thumbnail">Thumbnail</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.thumbnail}</dd>
            <dt>
              <span id="poster">
                <Translate contentKey="novelasApp.telenovela.poster">Poster</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.poster}</dd>
            <dt>
              <span id="background">
                <Translate contentKey="novelasApp.telenovela.background">Background</Translate>
              </span>
            </dt>
            <dd>{telenovelaEntity.background}</dd>
            <dt>
              <Translate contentKey="novelasApp.telenovela.actor">Actor</Translate>
            </dt>
            <dd>
              {telenovelaEntity.actors
                ? telenovelaEntity.actors.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === telenovelaEntity.actors.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/telenovela" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/telenovela/${telenovelaEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ telenovela }: IRootState) => ({
  telenovelaEntity: telenovela.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TelenovelaDetail);
