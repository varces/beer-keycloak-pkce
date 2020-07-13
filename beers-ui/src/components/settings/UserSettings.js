import React, { Component } from 'react'
import { withKeycloak } from '@react-keycloak/web'
import { beersApi } from '../misc/BeersApi'
import { handleLogError } from '../misc/Helpers'
import { Container, Form, Segment, Button, Divider, Grid } from 'semantic-ui-react'
import { withRouter } from 'react-router-dom'

class UserSettings extends Component {
  state = {
    username: ''
  }

  async componentDidMount() {
    const { keycloak } = this.props

    try {
      const response = await beersApi.getUserExtrasMe(keycloak.token)
      const { username } = response.data
      this.setState({ username })
    } catch (error) {
      handleLogError(error)
    }
  }


  handleCancel = () => {
    this.props.history.push("/home")
  }

  handleSave = async () => {
    //const { keycloak } = this.props

    try {
      //await beersApi.saveUserExtrasMe(keycloak.token, userExtra)
      this.props.history.push("/home")
    } catch (error) {
      handleLogError(error)
    }
  }

  handleImageLoad = () => {
    this.setState({ imageLoading: false })
  }

  render() {

    return (
      <Container>
        <Grid centered>
          <Grid.Row>
            <Segment style={{ width: '330px' }}>
              <Form>
                <Divider />
                <Divider />
                <Button.Group fluid>
                  <Button onClick={this.handleCancel}>Cancel</Button>
                  <Button.Or />
                  <Button onClick={this.handleSave} positive>Save</Button>
                </Button.Group>
              </Form>
            </Segment>
          </Grid.Row>
        </Grid>
      </Container>
    )
  }
}

export default withRouter(withKeycloak(UserSettings))
