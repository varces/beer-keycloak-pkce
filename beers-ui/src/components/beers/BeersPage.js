import React, { Component } from 'react'
import { withKeycloak } from '@react-keycloak/web'
import { Container, Grid, Header, Segment, Icon, Divider } from 'semantic-ui-react'
import { handleLogError } from '../misc/Helpers'
import { beersApi } from '../misc/BeersApi'
import BeersForm from './BeersForm'
import BeersTable from './BeersTable'
import { isAdmin } from '../misc/Helpers'
import { Redirect } from 'react-router-dom'
import ConfirmationModal from '../misc/ConfirmationModal'

class BeersPage extends Component {
  formInitialState = {
    id: '',
    name: '',
    description: '',
    tagline: '',
    image_url: '',

    idError: false,
    nameError: false,
    descriptionError: false,
    taglineError: false
  }

  modalInitialState = {
    isOpen: false,
    header: '',
    content: '',
    onAction: null,
    onClose: null
  }

  state = {
    beers: [],
    form: { ...this.formInitialState },
    modal: { ...this.modalInitialState },
    deleteBeer: null,
  }

  async componentDidMount() {
    this.handleGetBeers()
  }

  handleChange = (e) => {
    const { id, value } = e.target
    const form = { ...this.state.form }
    form[id] = value
    this.setState({ form })
  }

  handleGetBeers = async () => {
    try {
      const response = await beersApi.getBeers()
      const beers = response.data
      this.setState({ beers })
    } catch (error) {
      handleLogError(error)
    }
  }

  handleSaveBeer = async () => {
    if (!this.isValidForm()) {
      return
    }

    const { keycloak } = this.props
    const { id, name, description, tagline, image_url } = this.state.form
    
    const beer = { id, name, description, tagline, image_url }
    try {
      await beersApi.saveBeer(beer, keycloak.token)
      this.clearForm()
      this.handleGetBeers()
    } catch (error) {
      handleLogError(error)
    }
  }

  handleDeleteBeer = (beer) => {
    const modal = {
      isOpen: true,
      header: 'Delete Beer',
      content: `Would you like to delete beer '${beer.title}'?`,
      onAction: this.handleActionModal,
      onClose: this.handleCloseModal
    }
    this.setState({ modal, deleteBeer: beer })
    // The deletion is done in handleActionModal function
  }

  handleEditBeer = (beer) => {
    const form = {
      id: beer.id,
      name: beer.name,
      description: beer.description,
      tagline: beer.tagline,
      image_url: beer.image_url,
      idError: false,
      nameError: false,
      descriptionError: false,
      taglineError: false
    }
    this.setState({ form })
  }

  clearForm = () => {
    this.setState({ form: { ...this.formInitialState } })
  }

  isValidForm = () => {
    const form = { ...this.state.form }
    const idError = form.id.trim() === ''
    const nameError = form.name.trim() === ''
    const descriptionError = form.description.trim() === ''
    const taglineError = form.tagline.trim() === ''

    form.idError = idError
    form.nameError = nameError
    form.descriptionError = descriptionError
    form.taglineError = taglineError

    this.setState({ form })
    return (idError || nameError || descriptionError || taglineError) ? false : true
  }

  handleActionModal = async (response) => {
    if (response) {
      const { keycloak } = this.props
      const { deleteBeer } = this.state

      try {
        await beersApi.deleteBeer(deleteBeer.id, keycloak.token)
        this.handleGetBeers()
      } catch (error) {
        handleLogError(error)
      }
    }
    this.setState({ modal: { ...this.modalInitialState } })
  }

  handleCloseModal = () => {
    this.setState({ modal: { ...this.modalInitialState } })
  }

  render() {
    const { keycloak } = this.props
    if (!isAdmin(keycloak)) {
      return <Redirect to='/' />
    }

    const { beers, form, modal } = this.state
    return (
      <Container>
        <Grid>
          <Grid.Column mobile={16} tablet={16} computer={4}>
            <Segment>
              <Header as='h2'>
                <Icon name='beer' />
                <Header.Content>Beers</Header.Content>
              </Header>
              <Divider />
              <BeersForm
                form={form}
                handleChange={this.handleChange}
                handleSaveBeer={this.handleSaveBeer}
                clearForm={this.clearForm}
              />
            </Segment>
          </Grid.Column>
          <Grid.Column mobile={16} tablet={16} computer={12}>
            <BeersTable
              beers={beers}
              handleDeleteBeer={this.handleDeleteBeer}
              handleEditBeer={this.handleEditBeer}
            />
          </Grid.Column>
        </Grid>

        <ConfirmationModal modal={modal} />
      </Container>
    )
  }
}

export default withKeycloak(BeersPage)