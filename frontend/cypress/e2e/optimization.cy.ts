import { getOptimization } from '../factories/optimization'

describe('Optimization view', () => {
  beforeEach(() => {
    cy.intercept('GET', '**/inventory?parametersId=*', { ...getOptimization() }).as('purchases')
  })

  it('Retrieves and display the purchase schedule', () => {
    cy.visit('/')

    cy.wait('@purchases')

    cy.get('[data-testid="parameters-form"]').should('be.visible')
    cy.get('[id="optimization-chart"]').should('be.visible')
    cy.get('[data-testid="purchase-schedule-table"').should('be.visible')
    cy.get('[data-testid="purchase-schedule-table"] tbody tr').should('have.length', 15)
    cy.get('[data-testid="table-item-0-purchaseDate"]').should(
      'contain.text',
      'Sunday 5 January 2025',
    )
    cy.get('[data-testid="table-item-0-orderAmount"]').should('contain.text', '34')
    cy.get('[data-testid="table-item-0-currentStock"]').should('contain.text', '2')
  })

  it('Displays the next pagination page', () => {
    cy.visit('/')

    cy.wait('@purchases')

    cy.get('[data-testid="next-page"]').click()

    cy.get('[data-testid="table-item-0-purchaseDate"]').should(
      'contain.text',
      'Monday 20 January 2025',
    )
    cy.get('[data-testid="table-item-0-orderAmount"]').should('contain.text', '0')
    cy.get('[data-testid="table-item-0-currentStock"]').should('contain.text', '10')
  })

  it('Updates the purchase schedule when changing the parameters', () => {
    cy.intercept('PUT', '**/inventory', {
      ...getOptimization({
        inventoryParameters: {
          ...getOptimization().inventoryParameters,
          currentStock: '0',
        },
        purchaseSchedule: [
          { ...getOptimization().purchaseSchedule[0], currentStock: 0 },
          ...getOptimization().purchaseSchedule.slice(1),
        ],
      }),
    }).as('updatePurchases')

    cy.visit('/')

    cy.wait('@purchases')

    cy.get('[data-testid="current-stock-input"]').clear()
    cy.get('[data-testid="current-stock-input"]').type('10')

    cy.get('[data-testid="update-optimization-button"]').click()
  })
})
