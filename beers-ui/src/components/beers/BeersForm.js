import React from 'react'
import { Button, Form } from 'semantic-ui-react'

function BeersForm({ form, handleChange, handleSaveBeer, clearForm }) {
  return (
    <Form>
      <Form.Input
        fluid
        label='Id *'
        id='id'
        onChange={handleChange}
        value={form.id}
        error={form.idError}
      />
      <Form.Input
        fluid
        label='Name *'
        id='name'
        onChange={handleChange}
        value={form.name}
        error={form.nameError}
      />
      <Form.Input
        fluid
        label='Description *'
        id='description'
        onChange={handleChange}
        value={form.description}
        error={form.descriptionError}
      />
      <Form.Input
        fluid
        label='Tag Line *'
        id='tagline'
        onChange={handleChange}
        value={form.tagline}
        error={form.taglineError}
      />
      <Form.Input
        fluid
        label='Image URL'
        id='image_url'
        onChange={handleChange}
        value={form.image_url}
      />
      <Button.Group fluid>
        <Button onClick={clearForm}>Cancel</Button>
        <Button.Or />
        <Button positive onClick={handleSaveBeer}>Save</Button>
      </Button.Group>
    </Form>
  )
}

export default BeersForm