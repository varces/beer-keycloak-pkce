import React from 'react'
import { KeycloakProvider } from '@react-keycloak/web'
import Keycloak from 'keycloak-js'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Home from './components/home/Home'
import { beersApi } from './components/misc/BeersApi'
import Navbar from './components/misc/Navbar'
import PrivateRoute from './components/misc/PrivateRoute'
import BeersPage from './components/beers/BeersPage'
import UserSettings from './components/settings/UserSettings'
import BeerDetail from './components/beer/BeerDetail'
import { Dimmer, Header, Icon } from 'semantic-ui-react'
import { config } from './Constants'

function App() {
  const keycloak = new Keycloak({
    url: `${config.url.KEYCLOAK_BASE_URL}/auth`,
    realm: "beer-services",
    clientId: "beer-app"
  })
   const initConfig = { pkceMethod: 'S256', checkLoginIframe: false }

  const handleOnEvent = async (event, error) => {
    if (event === 'onAuthSuccess') {
      if (keycloak.authenticated) {
        let response = await beersApi.getUserExtrasMe(keycloak.token)
        if (response.status === 404) {
          const userExtra = { avatar: keycloak.tokenParsed.preferred_username }
          response = await beersApi.saveUserExtrasMe(keycloak.token, userExtra)
          console.log('UserExtra created for ' + keycloak.tokenParsed.preferred_username)
        }
        keycloak['avatar'] = response.data.avatar
      }
    }
  }

  const loadingComponent = (
    <Dimmer inverted active={true} page>
      <Header style={{ color: '#4d4d4d' }} as='h2' icon inverted>
        <Icon loading name='cog' />
        <Header.Content>Keycloak is loading
          <Header.Subheader style={{ color: '#4d4d4d' }}>or running authorization</Header.Subheader>
        </Header.Content>
      </Header>
    </Dimmer>
  )

  return (
    <KeycloakProvider
      keycloak={keycloak}
      initConfig={initConfig}
      LoadingComponent={loadingComponent}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <Navbar />
        <Switch>
          <Route path='/' exact component={Home} />
          <Route path='/home' component={Home} />
          <Route path='/beers/:id' component={BeerDetail} />
          <PrivateRoute path='/beers' component={BeersPage} />
          <PrivateRoute path='/settings' component={UserSettings} />
          <Route component={Home} />
        </Switch>
      </Router>
    </KeycloakProvider>
  )
}

export default App
