import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import ExpenseTracker from "./component/ExpensesTracker";
import Register from "./pages/Register";
import UserTracker from "./component/UserTracker";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/expenses" element={<ExpenseTracker />} />
        <Route path="/users" element={<UserTracker />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
