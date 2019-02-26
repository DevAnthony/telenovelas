import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Episode from './episode';
import EpisodeDetail from './episode-detail';
import EpisodeUpdate from './episode-update';
import EpisodeDeleteDialog from './episode-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EpisodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EpisodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EpisodeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Episode} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EpisodeDeleteDialog} />
  </>
);

export default Routes;
