import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function ExpenseTracker() {
  const [users, setUsers] = useState([]);
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
    role: "USER",
  });

  const [show, setShow] = useState(true);

  const navigate = useNavigate();

  const BASE_URL = import.meta.env.VITE_BASE_URL;
  const CONTEXT = import.meta.env.VITE_CONTEXT_PATH;
  const API = import.meta.env.VITE_API_URI;

  useEffect(() => {
    const userURL = `${BASE_URL}/${CONTEXT}/${API}/admin/get_users`;
    console.log(userURL);
    const getUsers = async () => {
      try {
        const response = await fetch(userURL, {
          method: "GET",
          credentials: "include",
        });
        if (!response.ok) throw new Error("Failed to fetch users");

        const userData = await response.json();

        console.log(userData);

        setUsers(userData);
      } catch (error) {
        console.log("Error: " + error);
      }
    };
    getUsers();
  }, []);

  const addUser = async (e, newUser) => {
    // e.preventDefault();
    setUser(newUser);
    console.log(JSON.stringify(newUser));
    const createUserURL = `${BASE_URL}/${CONTEXT}/${API}/admin/create_user`;
    console.log(createUserURL);
    try {
      const response = await fetch(createUserURL, {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });
      if (!response.ok) throw new Error("Unable to add new user");
      const expenseData = await response.json();
      console.log(expenseData);
    } catch (error) {
      console.log(error);
    }
  };

  const updateUser = async (e) => {
    // e.preventDefault();
    console.log("User: " + JSON.stringify(user));
    const updateUserURL = `${BASE_URL}/${CONTEXT}/${API}/admin/update_user/${user.id}`;
    console.log("user: " + updateUserURL);
    try {
      const response = await fetch(updateUserURL, {
        method: "PUT",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });
      if (!response.ok) throw new Error("Unable to update user");
      else setShow(true);
    } catch (error) {
      console.log(error);
    }
  };

  const editUser = (user) => {
    setShow(false);
    setUser(user);
    console.log("User in edit: " + JSON.stringify(user));
  };

  const deleteUser = async (id) => {
    console.log("User " + id + " delete");
    try {
      const del = await fetch(
        `${BASE_URL}/${CONTEXT}/${API}/admin/delete_user/` + id,
        {
          method: "DELETE",
          credentials: "include",
        },
      );
      if (del.ok) {
        setUsers((prevUser) => prevUser.filter((user) => user.id != id));
      } else {
        throw new Error(id + "User doesn't exist");
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
      <div className="m-3 justify-items-end">
        <div>
          <button
            className="bg-red-500 text-white pb-1 pt-1 pr-3 pl-3 rounded-2xl hover:bg-red-900"
            onClick={() => logout()}
          >
            ╰┈➤ Logout
          </button>
        </div>
      </div>
      <div className="mt-4 items-center justify-center">
        <form onSubmit={addUser}>
          <div className="flex flex-row place-content-evenly mb-10 ml-5 mr-5 p-3 rounded-xl border-2 shadow-2xl">
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="text"
                placeholder="name"
                onChange={(e) =>
                  setUser({
                    ...user,
                    name: e.target.value,
                  })
                }
              />
            </div>
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="text"
                placeholder="email"
                onChange={(e) =>
                  setUser({
                    ...user,
                    email: e.target.value,
                  })
                }
              />
            </div>
            <div>
              <input
                className="rounded-2xl border-2 p-2 mt-1"
                type="password"
                placeholder="password"
                onChange={(e) =>
                  setUser({
                    ...user,
                    password: e.target.value,
                  })
                }
              />
            </div>
            <div>
              <select
                className="rounded-2xl border-2 p-2 mt-1"
                onChange={(e) =>
                  setUser({
                    ...user,
                    role: e.target.value,
                  })
                }
              >
                <option value="USER">User</option>
                <option value="ADMIN">Admin</option>
              </select>
            </div>
            <div>
              <button
                className="bg-blue-600 text-white p-2 mt-1 rounded-2xl hover:bg-blue-900"
                type="submit"
              >
                Add User
              </button>
            </div>
          </div>
        </form>
      </div>
      <div className="flex flex-col items-center justify-center">
        <div className="text-3xl mb-10 p-3 rounded-b-xl shadow-2xl">
          <h2>Users and Admins</h2>
        </div>

        <div className="overflow-hidden rounded-xl shadow-xl border border-black">
          <table className="border-collapse">
            <thead>
              <tr>
                <th className="px-6 py-3 border-r border-gray-500">Name</th>
                <th className="px-6 py-3 border-r border-gray-500">E-mail</th>
                <th className="px-6 py-3 border-r border-gray-500">Role</th>
                <th className="px-6 py-3">Action</th>
              </tr>
            </thead>

            <tbody>
              {users.map((u) => (
                <tr
                  className="border-t border-black hover:bg-gray-100 transition-colors"
                  key={u.id}
                >
                  <td className="px-6 py-3 text-center border-r border-black">
                    {u.name}
                  </td>

                  <td className="px-6 py-3 border-r border-black">{u.email}</td>

                  <td className="px-6 py-3 text-center border-r border-black">
                    {u.role}
                  </td>

                  <td className="px-6 py-3">
                    <div className="flex gap-3 justify-center">
                      <button
                        className="cursor-pointer hover:scale-125 transition-transform"
                        onClick={() => editUser(u)}
                      >
                        ✏️
                      </button>

                      <button
                        className="cursor-pointer hover:scale-125 transition-transform"
                        onClick={() => deleteUser(u.id)}
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
      <form onSubmit={updateUser}>
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
              placeholder="username"
              value={user?.name || ""}
              onChange={(e) => setUser({ ...user, name: e.target.value })}
            />
          </div>
          <div>
            <input
              className="rounded-2xl border-2 p-2 mt-1"
              type="text"
              placeholder="email"
              value={user?.email || ""}
              onChange={(e) =>
                setUser({
                  ...user,
                  email: e.target.value,
                })
              }
            />
          </div>
          <div>
            <select
              className="rounded-2xl border-2 p-2 mt-1"
              onChange={(e) =>
                setUser({
                  ...user,
                  role: e.target.value,
                })
              }
            >
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
            </select>
          </div>
          <div>
            <button
              className="bg-blue-600 text-white p-2 mt-1 rounded-2xl hover:bg-blue-900"
              type="submit"
              onClick={() => {
                console.log("Button clicked");
              }}
            >
              Update User
            </button>
          </div>
        </div>
      </form>
    </>
  );
}

export default ExpenseTracker;
