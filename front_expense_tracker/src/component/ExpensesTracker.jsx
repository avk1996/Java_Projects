import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function ExpenseTracker() {
  const [expenses, setExpenses] = useState([]);
  const [expense, setExpense] = useState({
    amount: "",
    description: "",
    spendingDate: "",
    spendingTime: "",
  });
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [spendingDate, setSpendingDate] = useState("");
  const [spendingTime, setSpendingTime] = useState("");
  const [show, setShow] = useState(true);

  const navigate = useNavigate();

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

        // console.log(expenseData);

        setExpenses(expenseData);
      } catch (error) {
        console.log("Error: " + error);
      }
    };
    getExpenses();
  }, []);

  const addExpense = async (e) => {
    console.log("added expense");
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
      console.log(error);
    }
  };

  const updateExpense = async (e) => {
    console.log("Expense: " + JSON.stringify(expense));
    try {
      const response = await fetch(
        "http://localhost:8080/expense_tracker_hub/api/expense/update_expense/" +
          expense.id,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(expense),
        },
      );
      if (!response.ok) throw new Error("Unable to update expense");
      else setShow(true);
    } catch (error) {
      console.log(error);
    }
  };

  const editExpense = (expense) => {
    setShow(false);
    setExpense(expense);
    console.log("Expense in edit: " + JSON.stringify(expense));
  };

  const deleteExpense = async (id) => {
    console.log("Expense " + id + " delete");
    try {
      const del = await fetch(
        "http://localhost:8080/expense_tracker_hub/api/expense/delete_expense/" +
          id,
        {
          method: "DELETE",
          credentials: "include",
        },
      );
      if (del.ok) {
        setExpenses((prevExpense) =>
          prevExpense.filter((expense) => expense.id != id),
        );
      } else {
        throw new Error(id + " expense doesn't exist");
      }
    } catch (error) {
      console.log(error);
    }
  };

  const logout = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/expense_tracker_hub/logout",
        {
          method: "PUT",
          credentials: "include",
        },
      );
      if (!response.ok) throw new Error("unable to logout");
      navigate("/login");
    } catch (error) {
      console.log("Unable to log out");
    }
  };

  return (
    <>
      <div>
        <button onClick={() => logout()}>Logout</button>
      </div>
      <div>
        <form onSubmit={addExpense}>
          <input
            type="text"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
          <input
            type="text"
            placeholder="description"
            value={description}
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
          <button
            type="submit"
            onClick={() => {
              console.log("Button clicked");
            }}
          >
            Add Expense
          </button>
        </form>
      </div>
      <h2 className="text-2xl font-bold mb-4">My Expenses</h2>
      <table>
        <thead>
          <tr>
            <th>Amount</th>
            <th>Description</th>
            <th>Spending time</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {expenses.map((expense) => (
            <tr key={expense.id}>
              <td>{expense.amount}</td>
              <td>{expense.description}</td>
              <td>
                {expense.spendingDate} {expense.spendingTime}
              </td>
              <td
                className="cursor-pointer"
                onClick={() => editExpense(expense)}
              >
                ✏️
              </td>
              <td
                className="cursor-pointer"
                onClick={() => deleteExpense(expense.id)}
              >
                🧹
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div hidden={show}>
        <form onSubmit={updateExpense}>
          <input
            type="text"
            placeholder="Amount"
            value={expense.amount}
            onChange={(e) => setExpense({ ...expense, amount: e.target.value })}
          />
          <input
            type="text"
            placeholder="description"
            value={expense.description}
            onChange={(e) =>
              setExpense({
                ...expense,
                description: e.target.value,
              })
            }
          />
          <input
            type="text"
            placeholder="spendingDate"
            value={new Date().toISOString().split("T")[0]}
            onChange={() =>
              setExpense({
                ...expense,
                spendingDate: new Date().toISOString().split("T")[0],
              })
            }
          />
          <input
            type="text"
            placeholder="spendingTime"
            value={new Date().toTimeString().split(" ")[0]}
            onChange={() =>
              setExpense({
                ...expense,
                spendingTime: new Date().toTimeString().split(" ")[0],
              })
            }
          />
          <button
            type="submit"
            onClick={() => {
              console.log("Button clicked");
            }}
          >
            Update Expense
          </button>
        </form>
      </div>
    </>
  );
}

export default ExpenseTracker;
