import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './episode.reducer';
import { IEpisode } from 'app/shared/model/episode.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEpisodeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EpisodeDetail extends React.Component<IEpisodeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { episodeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="novelasApp.episode.detail.title">Episode</Translate> [<b>{episodeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="number">
                <Translate contentKey="novelasApp.episode.number">Number</Translate>
              </span>
            </dt>
            <dd>{episodeEntity.number}</dd>
            <dt>
              <span id="title">
                <Translate contentKey="novelasApp.episode.title">Title</Translate>
              </span>
            </dt>
            <dd>{episodeEntity.title}</dd>
            <dt>
              <span id="rating">
                <Translate contentKey="novelasApp.episode.rating">Rating</Translate>
              </span>
            </dt>
            <dd>{episodeEntity.rating}</dd>
            <dt>
              <span id="downloadlink">
                <Translate contentKey="novelasApp.episode.downloadlink">Downloadlink</Translate>
              </span>
            </dt>
            <dd>{episodeEntity.downloadlink}</dd>
            <dt>
              <span id="streaminglink">
                <Translate contentKey="novelasApp.episode.streaminglink">Streaminglink</Translate>
              </span>
            </dt>
            <dd>{episodeEntity.streaminglink}</dd>
            <dt>
              <Translate contentKey="novelasApp.episode.actor">Actor</Translate>
            </dt>
            <dd>
              {episodeEntity.actors
                ? episodeEntity.actors.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === episodeEntity.actors.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="novelasApp.episode.telenovela">Telenovela</Translate>
            </dt>
            <dd>{episodeEntity.telenovelaId ? episodeEntity.telenovelaId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/episode" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/episode/${episodeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ episode }: IRootState) => ({
  episodeEntity: episode.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EpisodeDetail);
