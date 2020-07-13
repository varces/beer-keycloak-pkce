import React from 'react'
import { Card, Header, Segment } from 'semantic-ui-react'
import BeerCard from './BeerCard'

function BeerList({ beers }) {
  const beerList = beers.map(beer => <BeerCard key={beer.id} beer={beer} link={true} />)

  return (
    beers.length > 0 ? (
      <Card.Group doubling centered>
        {beerList}
      </Card.Group >
    ) : (
        <Segment padded color='green'>
          <Header textAlign='center' as='h4'>No beers</Header>
        </Segment>
      )
  )
}

export default BeerList