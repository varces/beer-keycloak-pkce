import React from 'react'
import { Button, Image, Table } from 'semantic-ui-react'

function BeersTable({ beers, handleDeleteBeer, handleEditBeer }) {
  const height = window.innerHeight - 100
  const style = {
    height: height,
    maxHeight: height,
    overflowY: 'auto',
    overflowX: 'hidden'
  }

  const beerList = beers && beers.map(beer => {
    return (
      <Table.Row key={beer.id}>
        <Table.Cell collapsing>
          <Button
            circular
            color='red'
            size='small'
            icon='trash'
            onClick={() => handleDeleteBeer(beer)}
          />
          <Button
            circular
            color='orange'
            size='small'
            icon='edit'
            onClick={() => handleEditBeer(beer)}
          />
        </Table.Cell>
        <Table.Cell>{beer.id}</Table.Cell>
        <Table.Cell>{beer.name}</Table.Cell>
        <Table.Cell>{beer.description}</Table.Cell>
        <Table.Cell>{beer.tagline}</Table.Cell>
        <Table.Cell>
          <Image size='tiny' src={beer.image_url ? beer.image_url : '/images/beer-image.jpg'} rounded />
        </Table.Cell>
      </Table.Row>
    )
  })

  return (
    <div style={style}>
      <Table compact striped>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={2}/>
            <Table.HeaderCell width={2}>Id</Table.HeaderCell>
            <Table.HeaderCell width={4}>Name</Table.HeaderCell>
            <Table.HeaderCell width={3}>Description</Table.HeaderCell>
            <Table.HeaderCell width={2}>TagLine</Table.HeaderCell>
            <Table.HeaderCell width={3}>Image</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {beerList}
        </Table.Body>
      </Table>
    </div>
  )
}

export default BeersTable