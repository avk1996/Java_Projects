import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Password() {
  const BASE_URL = import.meta.env.VITE_BASE_URL;
  const CONTEXT = import.meta.env.VITE_CONTEXT_PATH;
  const API = import.meta.env.VITE_API_URI;

  const [user, setUser] = useState({
    identifier: "",
    oldPassword: "",
    newPassword: "",
  });

  const [confirmPassword, setConfirmPassword] = useState("");

  const navigate = useNavigate();
  const backToHome = () => {
    navigate("/login");
  };

  const resetPassword = async (e) => {
    e.preventDefault();
    console.log("resetPassword clicked");
    try {
      if (user.oldPassword === user.newPassword)
        throw new Error("Old password and New password should not be same");

      if (user.newPassword !== confirmPassword)
        throw new Error("Password did not matched");

      const resetPassURL = `${BASE_URL}/${CONTEXT}/${API}/auth/reset_password`;

      const resetJSON = JSON.stringify(user);
      console.log(
        "Reset JSON: " + resetJSON + ", confirm : " + confirmPassword,
      );

      // check if the login details are good
      const response = await fetch(resetPassURL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: resetJSON,
      });
      if (!response.ok) throw new Error("Unable to reset password");
    } catch (error) {
      console.log(error);
    }
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
            <form onSubmit={resetPassword}>
              <div className="mt-2 mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="text"
                  placeholder="Username or Email"
                  onChange={(e) =>
                    setUser({
                      ...user,
                      identifier: e.target.value,
                    })
                  }
                  required
                />
              </div>
              <div className="mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="password"
                  placeholder="Password"
                  onChange={(e) =>
                    setUser({ ...user, oldPassword: e.target.value })
                  }
                  required
                />
              </div>
              <div className="mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="password"
                  placeholder="New Password"
                  onChange={(e) =>
                    setUser({ ...user, newPassword: e.target.value })
                  }
                  required
                />
              </div>
              <div className="mb-2">
                <input
                  className="rounded-2xl border-2 p-2 mt-1"
                  type="password"
                  placeholder="Confirm Password"
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  required
                />
              </div>
              <div className="mb-2">
                <button
                  className="w-full bg-blue-600 text-white pb-1 pt-1 pr-3 pl-3 mt-3 rounded-2xl hover:bg-blue-900"
                  type="submit"
                >
                  🚪Reset Password
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}

export default Password;
