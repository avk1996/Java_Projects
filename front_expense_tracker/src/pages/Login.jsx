import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();

    console.log("username: " + username);

    const data = new URLSearchParams();

    data.append("username", username);
    data.append("password", password);

    try {
      const response = await fetch(
        "http://localhost:8080/expense_tracker_hub/api/expense/auth/login",
        {
          method: "post",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: data,
          credentials: "include",
        },
      );
      if (response.ok) {
        navigate("/expenses");
      }
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
      <div>
        <h1 className="text-2xl font-bold text-center md-6">Expense Tracker</h1>
      </div>
      <div>
        <form onSubmit={handleLogin}>
          <div className="flex flex-col p-5">
            <input
              type="text"
              placeholder="Username or Email"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div>
            <button
              className="bg-blue-600 text-white p-3 rounded hover:bg-blue-900"
              type="sumbit"
            >
              Login
            </button>
          </div>
        </form>
      </div>
      <div>
        <p>
          Don't have an account?
          <button
            className="bg-green-600 text-white p-3 rounded hover:bg-green-900"
            type="sumbit"
            onClick={() => navigate("/register")}
          >
            Register
          </button>
        </p>
      </div>
    </div>
  );
}

export default Login;
