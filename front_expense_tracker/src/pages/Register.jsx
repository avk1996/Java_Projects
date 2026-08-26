import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
  });

  const BASE_URL = import.meta.env.VITE_BASE_URL;
  const CONTEXT = import.meta.env.VITE_CONTEXT_PATH;
  const API = import.meta.env.VITE_API;

  const navigate = useNavigate();

  const addUser = async (e) => {
    console.log("User: " + JSON.stringify(user));

    try {
      const response = await fetch(`${BASE_URL}/${CONTEXT}/${API}/add_user`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });

      if (!response.ok) throw new Error("Unable to add user");
    } catch (error) {
      console.log(error);
    }
  };

  const backToHome = () => {
    navigate("/login");
  };

  return (
    <>
      <div className="min-h-screen flex flex-col items-center justify-center border-2">
        <div className="border-2 border-black rounded-xl p-10 shadow-2xl">
          <div className="mt-2">
            <button
              className="bg-red-500 text-white pb-1 pt-1 pr-3 pl-3 rounded-2xl hover:bg-red-900"
              onClick={backToHome}
            >
              ╰┈➤Back
            </button>
          </div>
          <div className="flex flex-col">
            <form onSubmit={addUser}>
              <div className="mt-2 mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="text"
                  placeholder="username"
                  onChange={(e) =>
                    setUser({
                      ...user,
                      name: e.target.value,
                    })
                  }
                />
              </div>
              <div className="mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="text"
                  placeholder="email"
                  onChange={(e) => setUser({ ...user, email: e.target.value })}
                />
              </div>
              <div className="mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="password"
                  placeholder="password"
                  onChange={(e) =>
                    setUser({ ...user, password: e.target.value })
                  }
                />
              </div>
              <div className="mb-2">
                <button
                  className="bg-blue-600 text-white pb-1 pt-1 pr-3 pl-3 rounded-2xl hover:bg-blue-900"
                  type="submit"
                >
                  🚪Add User
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}

export default Register;
