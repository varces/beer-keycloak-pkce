import React from 'react'
import { Card, Image } from 'semantic-ui-react'
import { Link } from 'react-router-dom'

function BeerCard({ beer, link }) {
  const content = (
    <>
      <Image src={beer.image_url ? beer.image_url : '/images/beer-image.jpg'} wrapped ui={false} />
      <Card.Content textAlign="center">
        <Card.Header>{beer.name}</Card.Header>
      </Card.Content>
      <Card.Content>
        <Card.Description>id: <strong>{beer.id}</strong></Card.Description>
        <Card.Description>Description: <strong>{beer.description}</strong></Card.Description>
        <Card.Description>TagLine: <strong>{beer.tagline}</strong></Card.Description>
      </Card.Content>
    </>
  )
  return (
    !link ? <Card>{content}</Card> : <Card as={Link} to={`/beers/${beer.id}`}>{content}</Card>
  )
}

export default BeerCard