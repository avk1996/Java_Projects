import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

function ExpenseTracker() {
  const BASE_URL = import.meta.env.VITE_BASE_URL;
  const CONTEXT = import.meta.env.VITE_CONTEXT_PATH;
  const API = import.meta.env.VITE_API_URI;

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

  const location = useLocation();

  const state = location.state?.userType;

  const navigate = useNavigate();

  useEffect(() => {
    const now = new Date();

    const date = now.toISOString().split("T")[0];
    const time = now.toTimeString().split(" ")[0];

    setSpendingDate(date);
    setSpendingTime(time);

    const getExpenses = async () => {
      const expenseURL = `${BASE_URL}/${CONTEXT}/${API}/user/get_expense_records_user`;
      // console.log("Expense url: " + expenseURL);
      try {
        const response = await fetch(expenseURL, {
          method: "GET",
          credentials: "include",
        });
        if (!response.ok) throw new Error("Failed to fetch expenses");

        const expenseData = await response.json();

        // console.log(expenseData);

        setExpenses(expenseData);
      } catch (error) {
        console.log("Error: " + error);
      }
    };
    getExpenses();
  }, [amount, description]);

  const addExpense = async (e) => {
    // console.log("added expense");
    const expense = {
      amount: amount,
      description: description,
      spendingDate: spendingDate,
      spendingTime: spendingTime,
    };
    // console.log(JSON.stringify(expense));
    try {
      const response = await fetch(
        `${BASE_URL}/${CONTEXT}/${API}/user/add_expense`,
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
      // console.log(expenseData);
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
    const updateURL = `${BASE_URL}/${CONTEXT}/${API}/user/update_expense/${expense.id}`;
    // console.log(
    //   "Expense: " + JSON.stringify(expense) + ", update log: " + updateURL,
    // );
    try {
      const response = await fetch(updateURL, {
        method: "PUT",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(expense),
      });
      if (!response.ok) throw new Error("Unable to update expense");
      else setShow(true);
    } catch (error) {
      console.log(error);
    }
  };

  const editExpense = (expense) => {
    setShow(false);
    setExpense(expense);
    // console.log("Expense in edit: " + JSON.stringify(expense));
  };

  const deleteExpense = async (id) => {
    // console.log("Expense " + id + " delete");
    try {
      const del = await fetch(
        `${BASE_URL}/${CONTEXT}/${API}/user/delete_expense/` + id,
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
      const response = await fetch(`${BASE_URL}/${CONTEXT}/logout`, {
        method: "PUT",
        credentials: "include",
      });
      if (!response.ok) throw new Error("unable to logout");
      navigate("/login");
    } catch (error) {
      console.log("Unable to log out");
    }
  };

  return (
    <>
      <div className="flex flex-row m-3 justify-between">
        <div>
          <button
            className="bg-red-500 text-white pb-1 pt-1 pr-3 pl-3 rounded-2xl hover:bg-red-900"
            onClick={() => logout()}
          >
            ╰┈➤ Logout
          </button>
        </div>
        <div>
          <button
            hidden={state != "ADMIN"}
            className="bg-blue-500 text-white pb-1 pt-1 pr-3 pl-3 rounded-2xl hover:bg-blue-900"
            onClick={() => navigate("/users")}
          >
            Manage User
          </button>
        </div>
      </div>
      <div className="mt-4 items-center justify-center">
        <form onSubmit={addExpense}>
          <div className="flex flex-row place-content-evenly mb-10 ml-5 mr-5 p-3 rounded-xl border-2 shadow-2xl">
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="text"
                placeholder="Amount"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
              />
            </div>
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="text"
                placeholder="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="text"
                placeholder="spendingDate"
                value={spendingDate}
                readOnly
                disabled
              />
            </div>
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="text"
                placeholder="spendingTime"
                value={spendingTime}
                readOnly
                disabled
              />
            </div>
            <div>
              <button
                className="bg-blue-600 text-white p-2 mt-1 rounded-2xl hover:bg-blue-900"
                type="submit"
              >
                Add Expense
              </button>
            </div>
          </div>
        </form>
      </div>
      <div className="flex flex-col items-center justify-center">
        <div className="text-3xl mb-10 p-3 rounded-b-xl shadow-2xl">
          <h2>My Expenses</h2>
        </div>

        <div className="overflow-hidden rounded-xl shadow-xl border border-black">
          <table className="border-collapse">
            <thead>
              <tr>
                <th className="px-6 py-3 border-r border-gray-500">Amount</th>
                <th className="px-6 py-3 border-r border-gray-500">
                  Description
                </th>
                <th className="px-6 py-3 border-r border-gray-500">
                  Spending Time
                </th>
                <th className="px-6 py-3">Action</th>
              </tr>
            </thead>

            <tbody>
              {expenses.map((expense) => (
                <tr
                  key={expense.id}
                  className="border-t border-black hover:bg-gray-100 transition-colors"
                >
                  <td className="px-6 py-3 text-center border-r border-black">
                    ₹{expense.amount}
                  </td>

                  <td className="px-6 py-3 border-r border-black">
                    {expense.description}
                  </td>

                  <td className="px-6 py-3 text-center border-r border-black">
                    {expense.spendingDate} {expense.spendingTime}
                  </td>

                  <td className="px-6 py-3">
                    <div className="flex gap-3 justify-center">
                      <button
                        className="cursor-pointer hover:scale-125 transition-transform"
                        onClick={() => editExpense(expense)}
                      >
                        ✏️
                      </button>

                      <button
                        className="cursor-pointer hover:scale-125 transition-transform"
                        onClick={() => deleteExpense(expense.id)}
                      >
                        🧹
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      <form onSubmit={updateExpense}>
        <div
          className="flex flex-row place-content-evenly mt-10 mb-10 ml-5 mr-5 p-3 rounded-xl border-2 shadow-2xl"
          hidden={show}
        >
          <div>
            <button
              className="bg-green-500 text-white p-2 mt-1 rounded-2xl hover:bg-red-900"
              onClick={() => setShow(true)}
            >
              ❌️Cancel
            </button>
          </div>
          <div>
            <input
              className="rounded-2xl border-2 p-2 mt-1"
              type="text"
              placeholder="Amount"
              value={expense.amount}
              onChange={(e) =>
                setExpense({ ...expense, amount: e.target.value })
              }
            />
          </div>
          <div>
            <input
              className="rounded-2xl border-2 p-2 mt-1"
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
          </div>
          <div>
            <input
              className="rounded-2xl border-2 p-2 mt-1"
              type="text"
              placeholder="spendingDate"
              value={new Date().toISOString().split("T")[0]}
              onChange={() =>
                setExpense({
                  ...expense,
                  spendingDate: new Date().toISOString().split("T")[0],
                })
              }
              readOnly
              disabled
            />
          </div>
          <div>
            <input
              className="rounded-2xl border-2 p-2 mt-1"
              type="text"
              placeholder="spendingTime"
              value={new Date().toTimeString().split(" ")[0]}
              onChange={() =>
                setExpense({
                  ...expense,
                  spendingTime: new Date().toTimeString().split(" ")[0],
                })
              }
              readOnly
              disabled
            />
          </div>
          <div>
            <button
              className="bg-blue-600 text-white p-2 mt-1 rounded-2xl hover:bg-blue-900"
              type="submit"
            >
              Update Expense
            </button>
          </div>
        </div>
      </form>
    </>
  );
}

export default ExpenseTracker;
