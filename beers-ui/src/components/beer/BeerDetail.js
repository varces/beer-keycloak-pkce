import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'
import { Container, Grid } from 'semantic-ui-react'
import { handleLogError } from '../misc/Helpers'
import { beersApi } from '../misc/BeersApi'
import BeerCard from '../home/BeerCard'
import BeerComments from './BeerComments'
import BeerCommentForm from './BeerCommentForm'
import { withKeycloak } from '@react-keycloak/web'

class BeerDetail extends Component {
  state = {
    authenticated: false,
    beer: null,
    commentText: ''
  }

  async componentDidMount() {
    const id = this.props.match.params.id
    const { authenticated } = this.props.keycloak

    this.setState({ authenticated })
    try {
      const response = await beersApi.getBeer(id)
      const beer = response.data
      this.setState({ beer })
    } catch (error) {
      handleLogError(error)
    }
  }

  handleChange = (e) => {
    const { id, value } = e.target
    this.setState({ [id]: value })
  }

  handleAddComment = async () => {
    const { commentText } = this.state
    if (!commentText) {
      return
    }

    const { keycloak } = this.props
    let { beer } = this.state
    const comment = { text: commentText }
    try {
      const response = await beersApi.addBeerComment(beer.id, comment, keycloak.token)
      beer = response.data
      this.setState({ beer, commentText: '' })
    } catch (error) {
      handleLogError(error)
    }
  }

  render() {
    const { authenticated, beer, commentText } = this.state
    return (
      !beer ? <></> : (
        <Container>
          <Grid columns={2} stackable>
            <Grid.Row>
              <Grid.Column width={5}>
                <BeerCard beer={beer} link={false} />
              </Grid.Column>
              <Grid.Column width={11}>
                <BeerCommentForm
                  authenticated={authenticated}
                  commentText={commentText}
                  handleAddComment={this.handleAddComment}
                  handleChange={this.handleChange}
                />
                <BeerComments comments={beer.comments} />
              </Grid.Column>
            </Grid.Row>
          </Grid>
        </Container>
      )
    )
  }
}

export default withRouter(withKeycloak(BeerDetail))