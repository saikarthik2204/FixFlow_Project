import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";

const API_URL = "https://fixflow-backend-kq7z.onrender.com";

function CreateIssue() {
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState("MEDIUM");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleCreateIssue = async (event) => {
    event.preventDefault();

    setError("");
    setLoading(true);

    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    try {
      const response = await fetch(`${API_URL}/api/issues`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          title,
          description,
          priority,
          assignedToId: null,
        }),
      });

      if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("token");
        navigate("/login");
        return;
      }

      const data = await response.json();

      if (!response.ok) {
        throw new Error(
          data.message || "Failed to create issue"
        );
      }

      navigate("/dashboard");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="dashboard">
      <nav className="navbar">
        <div className="navbar-brand">
          <div className="logo">FixFlow</div>

          <span className="navbar-subtitle">
            Issue Management
          </span>
        </div>

        <button
          className="logout-button"
          onClick={handleLogout}
        >
          Logout
        </button>
      </nav>

      <main className="dashboard-content">
        <button
          className="back-button"
          onClick={() => navigate("/dashboard")}
        >
          ← Back to Dashboard
        </button>

        <div className="form-container">
          <div className="form-heading">
            <span className="form-label">
              NEW ISSUE
            </span>

            <h1>Create Issue</h1>

            <p>
              Report an issue and provide the details
              needed to track it.
            </p>
          </div>

          <form
            className="issue-form"
            onSubmit={handleCreateIssue}
          >
            <label htmlFor="title">
              Title
            </label>

            <input
              id="title"
              type="text"
              placeholder="Enter a short title"
              value={title}
              onChange={(event) =>
                setTitle(event.target.value)
              }
              required
            />

            <label htmlFor="description">
              Description
            </label>

            <textarea
              id="description"
              placeholder="Describe the issue in detail"
              value={description}
              onChange={(event) =>
                setDescription(event.target.value)
              }
              required
              rows="7"
            />

            <label htmlFor="priority">
              Priority
            </label>

            <select
              id="priority"
              value={priority}
              onChange={(event) =>
                setPriority(event.target.value)
              }
            >
              <option value="LOW">Low</option>
              <option value="MEDIUM">Medium</option>
              <option value="HIGH">High</option>
              <option value="CRITICAL">Critical</option>
            </select>

            {error && (
              <div className="form-error">
                {error}
              </div>
            )}

            <div className="form-actions">
              <button
                type="button"
                className="secondary-button"
                onClick={() => navigate("/dashboard")}
              >
                Cancel
              </button>

              <button
                type="submit"
                className="primary-form-button"
                disabled={loading}
              >
                {loading
                  ? "Creating..."
                  : "Create Issue"}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
}

export default CreateIssue;