import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const addUser = async (e) => {
    console.log("User: " + JSON.stringify(user));

    try {
      const response = await fetch(
        "http://localhost:8080/expense_tracker_hub/api/expense/add_user",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(user),
        },
      );

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
      <div>
        <button onClick={backToHome}>🏃‍♂️Back</button>
      </div>
      <div>
        <form onSubmit={addUser}>
          <input
            type="text"
            placeholder="username"
            onChange={(e) =>
              setUser({
                ...user,
                name: e.target.value,
              })
            }
          />
          <input
            type="text"
            placeholder="email"
            onChange={(e) => setUser({ ...user, email: e.target.value })}
          />
          <input
            type="password"
            placeholder="password"
            onChange={(e) => setUser({ ...user, password: e.target.value })}
          />
          <button type="submit">Add User</button>
        </form>
      </div>
    </>
  );
}

export default Register;
