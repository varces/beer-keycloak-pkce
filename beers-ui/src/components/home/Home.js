import React, { Component } from 'react'
import { Container } from 'semantic-ui-react'
import { handleLogError } from '../misc/Helpers'
import { beersApi } from '../misc/BeersApi'
import BeerList from './BeerList'

class Home extends Component {
  state = {
    isLoading: false,
    beers: []
  }

  async componentDidMount() {
    this.setState({ isLoading: true })
    try {
      const response = await beersApi.getBeers()
      const beers = response.data
      this.setState({ beers, isLoading: false })
    } catch (error) {
      handleLogError(error)
    }
  }

  render() {
    const { isLoading, beers } = this.state
    return (
      isLoading ? <></> : (
        <Container>
          <BeerList beers={beers} />
        </Container>
      )
    )
  }
}

export default Home