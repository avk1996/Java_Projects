import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/login";
import ExpenseTracker from "./component/ExpensesTracker";
import Register from "./pages/Register";

function App() {
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
