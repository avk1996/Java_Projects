import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [user, setUser] = useState({
    username: "",
    password: "",
  });

  const [userType, setUserType] = useState("");

  const navigate = useNavigate();

  const BASE_URL = import.meta.env.VITE_BASE_URL;
  const CONTEXT = import.meta.env.VITE_CONTEXT_PATH;
  const API = import.meta.env.VITE_API_URI;

  const handleLogin = async (event) => {
    event.preventDefault();

    const loginURL = `${BASE_URL}/${CONTEXT}/${API}/auth/login`;

    // console.log("Login: " + loginURL);
    // console.log("User: " + JSON.stringify(user));

    try {
      const response = await fetch(loginURL, {
        method: "post",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
        credentials: "include",
      });
      if (response.ok) {
        const result = await response.json();
        setUserType(result.role);
        navigate("/expenses", {
          state: { userType: result.role },
        });
      }
    } catch (error) {
      console.log("Error: " + error);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center border-2">
      <div className="text-3xl mb-10 p-3 rounded-b-xl shadow-2xl">
        <h1>Expense Tracker</h1>
      </div>
      <div className="border-2 border-black rounded-xl p-10 shadow-2xl">
        <div className="flex flex-col justify-center items-center">
          <form onSubmit={handleLogin}>
            <div>
              <input
                className="rounded-2xl border-2 p-2 mb-1"
                type="text"
                placeholder="Username or Email"
                onChange={(e) =>
                  setUser({
                    ...user,
                    username: e.target.value,
                  })
                }
              />
            </div>
            <div className="mb-4">
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="password"
                placeholder="Password"
                onChange={(e) =>
                  setUser({
                    ...user,
                    password: e.target.value,
                  })
                }
              />
              <p
                className="mt-1 text-center text-sm text-gray-500 hover:text-blue-600 cursor-pointer transition-colors duration-200"
                onClick={() => navigate("/reset_password")}
              >
                Forget Password?
              </p>
            </div>
            <div>
              <button
                className="w-full bg-blue-600 text-white pb-1 pt-1 pr-3 pl-3 rounded-xl hover:bg-blue-900"
                type="sumbit"
              >
                ⛩️Login
              </button>
            </div>
          </form>
        </div>
      </div>
      <div className="pt-2 mt-2">
        <p>
          Don't have an account?
          <button
            className="bg-green-600 text-white ml-1 pb-1 pt-1 pr-3 pl-3 rounded-2xl hover:bg-green-900"
            type="sumbit"
            onClick={() => navigate("/register")}
          >
            🌴Register
          </button>
        </p>
      </div>
    </div>
  );
}

export default Login;
