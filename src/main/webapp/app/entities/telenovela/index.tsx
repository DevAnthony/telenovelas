import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Telenovela from './telenovela';
import TelenovelaDetail from './telenovela-detail';
import TelenovelaUpdate from './telenovela-update';
import TelenovelaDeleteDialog from './telenovela-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TelenovelaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TelenovelaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TelenovelaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Telenovela} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TelenovelaDeleteDialog} />
  </>
);

export default Routes;
