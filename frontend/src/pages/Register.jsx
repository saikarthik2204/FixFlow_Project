import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../App.css";

const API_URL = "https://fixflow-backend-kq7z.onrender.com";

function Register() {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleRegister = async (event) => {
    event.preventDefault();

    setError("");
    setLoading(true);

    try {
      const response = await fetch(
        `${API_URL}/api/auth/register`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            name,
            email,
            password,
          }),
        }
      );

      const data = await response.json();

      if (!response.ok) {
        throw new Error(
          data.message || "Registration failed"
        );
      }

      localStorage.setItem("token", data.token);

      navigate("/dashboard");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-logo">FixFlow</div>

        <div className="auth-heading">
          <h1>Create an account</h1>
          <p>
            Create an account to start tracking issues.
          </p>
        </div>

        <form onSubmit={handleRegister}>
          <label htmlFor="name">Name</label>

          <input
            id="name"
            type="text"
            placeholder="Your name"
            value={name}
            onChange={(event) => setName(event.target.value)}
            required
          />

          <label htmlFor="email">Email</label>

          <input
            id="email"
            type="email"
            placeholder="you@example.com"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            required
          />

          <label htmlFor="password">Password</label>

          <input
            id="password"
            type="password"
            placeholder="Create a password"
            value={password}
            onChange={(event) =>
              setPassword(event.target.value)
            }
            required
          />

          {error && (
            <div className="auth-error">
              {error}
            </div>
          )}

          <button
            className="auth-submit"
            type="submit"
            disabled={loading}
          >
            {loading
              ? "Creating account..."
              : "Create Account"}
          </button>
        </form>

        <div className="auth-footer">
          <span>Already have an account?</span>

          <Link to="/login">
            Sign in
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Register;