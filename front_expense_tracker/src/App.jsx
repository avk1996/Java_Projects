import { useState } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/login";
import ExpenseTracker from "./pages/ExpensesTracker";
import Register from "./pages/Register";

function App() {
  const [count, setCount] = useState(0);

  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/expenses" element={<ExpenseTracker />} />
        </Routes>
      </BrowserRouter>
  );
}

export default App;
