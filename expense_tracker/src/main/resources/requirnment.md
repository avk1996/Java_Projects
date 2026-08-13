# Expense Tracker

## Functionality
### Users can
1. Add expense with description and amount.
2. Update an expense.
3. Delete and expense.
4. View his/her expense.
5. View Summary.
6. View Summary for specific month.

## Objects
## User
```json
{
  "id": Integer,
  "name": String,
}
```
## Expense
```json
{
  "id": Integer,
  "description": String,
  "spendingDate": LocalDate,
  "spendingTime": LocalTime,
  "amount": BigDecimal,
}
```