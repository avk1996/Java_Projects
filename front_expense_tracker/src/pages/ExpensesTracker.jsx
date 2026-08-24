import { useEffect, useState } from "react";

function ExpenseTracker() {
  const [expenses, setExpenses] = useState([]);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [spendingDate, setSpendingDate] = useState("");
  const [spendingTime, setSpendingTime] = useState("");

  useEffect(() => {
    const now = new Date();

    const date = now.toISOString().split("T")[0];
    const time = now.toTimeString().split(" ")[0];

    setSpendingDate(date);
    setSpendingTime(time);

    const getExpenses = async () => {
      try {
        const response = await fetch(
          "http://localhost:8080/expense_tracker_hub/api/expense/get_expense_records_user",
          {
            method: "GET",
            credentials: "include",
          },
        );
        if (!response.ok) throw new Error("Failed to fetch expenses");

        const expenseData = await response.json();

        console.log(expenseData);

        setExpenses(expenseData);
      } catch (error) {
        console.log("Error: " + error);
      }
    };
  }, []);

  const addExpense = async () => {
    const expense = {
      amount: amount,
      description: description,
      spendingDate: spendingDate,
      spendingTime: spendingTime,
    };
    console.log(JSON.stringify(expense));
    try {
      const response = await fetch(
        "http://localhost:8080/expense_tracker_hub/api/expense/add_expense",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
          body: JSON.stringify(expense),
        },
      );
      if (!response.ok) throw new Error("Unable to add expense");
      const expenseData = await response.json();
      console.log(expenseData);
      // clear form
      setAmount("");
      setDescription("");
      setSpendingDate("");
      setSpendingTime("");
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  return (
    <>
      <div>
        <form onSubmit={addExpense}>
          <input
            type="text"
            placeholder="Amount"
            onChange={(e) => setAmount(e.target.value)}
          />
          <input
            type="text"
            placeholder="description"
            onChange={(e) => setDescription(e.target.value)}
          />
          <input
            type="text"
            placeholder="spendingDate"
            value={spendingDate}
            readOnly
          />
          <input
            type="text"
            placeholder="spendingTime"
            value={spendingTime}
            readOnly
          />
          <button type="submit">Add Expense</button>
        </form>
      </div>
      <h2 className="text-2xl font-bold mb-4">My Expenses</h2>
      <thead>
        <tr>
          <th>amount</th>
          <th>description</th>
          <th>spending time</th>
        </tr>
      </thead>
      <tbody>
        {expenses.map((expense) => {
          <tr key={expense.id}>
            <td>{expense.amount}</td>
            <td>{expense.description}</td>
            <td>
              {expense.spendingDate} {expense.spendingTime}
            </td>
          </tr>;
        })}
      </tbody>
    </>
  );
}

export default ExpenseTracker;
