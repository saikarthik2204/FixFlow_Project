import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../App.css";

const API_URL = "https://fixflow-backend-kq7z.onrender.com";

function IssueDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [issue, setIssue] = useState(null);
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    const fetchIssue = async () => {
      try {
        const response = await fetch(
          `${API_URL}/api/issues/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 401 || response.status === 403) {
          localStorage.removeItem("token");
          navigate("/login");
          return;
        }

        if (response.status === 404) {
          throw new Error("Issue not found");
        }

        if (!response.ok) {
          throw new Error("Failed to load issue");
        }

        const data = await response.json();

        setIssue(data);
        setStatus(data.status);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchIssue();
  }, [id, navigate]);

  const handleStatusUpdate = async () => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    if (status === issue.status) {
      return;
    }

    setError("");
    setUpdating(true);

    try {
      const response = await fetch(
        `${API_URL}/api/issues/${id}/status?status=${status}`,
        {
          method: "PATCH",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("token");
        navigate("/login");
        return;
      }

      const data = await response.json();

      if (!response.ok) {
        throw new Error(
          data.message || "Failed to update issue status"
        );
      }

      setIssue(data);
      setStatus(data.status);
    } catch (err) {
      setError(err.message);
    } finally {
      setUpdating(false);
    }
  };

  const handleDelete = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this issue?"
    );

    if (!confirmed) {
      return;
    }

    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    setError("");
    setDeleting(true);

    try {
      const response = await fetch(
        `${API_URL}/api/issues/${id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("token");
        navigate("/login");
        return;
      }

      if (!response.ok) {
        const data = await response.json().catch(() => null);

        throw new Error(
          data?.message || "Failed to delete issue"
        );
      }

      navigate("/dashboard");
    } catch (err) {
      setError(err.message);
      setDeleting(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  if (loading) {
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
          <div className="loading-state">
            Loading issue...
          </div>
        </main>
      </div>
    );
  }

  if (error && !issue) {
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
          <div className="dashboard-error">
            {error}
          </div>

          <button
            className="back-button"
            onClick={() => navigate("/dashboard")}
          >
            ← Back to Dashboard
          </button>
        </main>
      </div>
    );
  }

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

        <div className="issue-details-card">
          <div className="details-top">
            <div>
              <span className="issue-number">
                Issue #{issue.id}
              </span>

              <h1>{issue.title}</h1>
            </div>

            <span
              className={`priority priority-${issue.priority.toLowerCase()}`}
            >
              {issue.priority}
            </span>
          </div>

          {error && (
            <div className="form-error">
              {error}
            </div>
          )}

          <div className="details-section">
            <span className="details-label">
              Status
            </span>

            <div className="status-control">
              <span
                className={`status status-${issue.status
                  .toLowerCase()
                  .replace("_", "-")}`}
              >
                {issue.status.replace("_", " ")}
              </span>

              <select
                value={status}
                onChange={(event) =>
                  setStatus(event.target.value)
                }
              >
                <option value="OPEN">OPEN</option>
                <option value="IN_PROGRESS">
                  IN PROGRESS
                </option>
                <option value="RESOLVED">
                  RESOLVED
                </option>
              </select>

              <button
                className="update-button"
                onClick={handleStatusUpdate}
                disabled={
                  updating ||
                  status === issue.status
                }
              >
                {updating
                  ? "Updating..."
                  : "Update"}
              </button>
            </div>
          </div>

          <div className="details-section description-section">
            <span className="details-label">
              Description
            </span>

            <p>{issue.description}</p>
          </div>

          <div className="issue-info-grid">
            <div>
              <span className="details-label">
                Created By
              </span>

              <strong>
                {issue.createdByName || "—"}
              </strong>
            </div>

            <div>
              <span className="details-label">
                Assigned To
              </span>

              <strong>
                {issue.assignedToName ||
                  "Unassigned"}
              </strong>
            </div>

            <div>
              <span className="details-label">
                Created
              </span>

              <strong>
                {new Date(
                  issue.createdAt
                ).toLocaleString()}
              </strong>
            </div>

            <div>
              <span className="details-label">
                Last Updated
              </span>

              <strong>
                {new Date(
                  issue.updatedAt
                ).toLocaleString()}
              </strong>
            </div>
          </div>

          <div className="danger-zone">
            <div>
              <h3>Delete Issue</h3>

              <p>
                This will permanently remove the issue.
              </p>
            </div>

            <button
              className="delete-button"
              onClick={handleDelete}
              disabled={deleting}
            >
              {deleting
                ? "Deleting..."
                : "Delete Issue"}
            </button>
          </div>
        </div>
      </main>
    </div>
  );
}

export default IssueDetails;