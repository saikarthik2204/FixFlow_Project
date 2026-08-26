import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const API_URL = "";

function Dashboard() {
  const navigate = useNavigate();

  const [issues, setIssues] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    const fetchIssues = async () => {
      try {
        const response = await fetch(`${API_URL}/api/issues`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.status === 401 || response.status === 403) {
          localStorage.removeItem("token");
          navigate("/login");
          return;
        }

        if (!response.ok) {
          throw new Error("Failed to load issues");
        }

        const data = await response.json();
        setIssues(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchIssues();
  }, [navigate]);

  const totalIssues = issues.length;

  const openIssues = issues.filter(
    (issue) => issue.status === "OPEN"
  ).length;

  const inProgressIssues = issues.filter(
    (issue) => issue.status === "IN_PROGRESS"
  ).length;

  const resolvedIssues = issues.filter(
    (issue) => issue.status === "RESOLVED"
  ).length;

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  const getPriorityClass = (priority) => {
    return `priority priority-${priority.toLowerCase()}`;
  };

  const getStatusClass = (status) => {
    return `status status-${status
      .toLowerCase()
      .replace("_", "-")}`;
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
        <div className="dashboard-header">
          <div>
            <h1>Dashboard</h1>

            <p>
              Manage and track your reported issues.
            </p>
          </div>

          <button
            className="create-issue-button"
            onClick={() => navigate("/issues/create")}
          >
            + Create Issue
          </button>
        </div>

        {loading && (
          <div className="loading-state">
            Loading issues...
          </div>
        )}

        {error && (
          <div className="dashboard-error">
            {error}
          </div>
        )}

        {!loading && !error && (
          <>
            <div className="stats-grid">
              <div className="stat-card">
                <span>Total Issues</span>
                <strong>{totalIssues}</strong>
              </div>

              <div className="stat-card">
                <span>Open</span>
                <strong>{openIssues}</strong>
              </div>

              <div className="stat-card">
                <span>In Progress</span>
                <strong>{inProgressIssues}</strong>
              </div>

              <div className="stat-card">
                <span>Resolved</span>
                <strong>{resolvedIssues}</strong>
              </div>
            </div>

            <section className="issues-section">
              <div className="section-header">
                <div>
                  <h2>My Issues</h2>
                  <p>
                    Issues reported from your account
                  </p>
                </div>

                <span className="issue-count">
                  {totalIssues}{" "}
                  {totalIssues === 1
                    ? "issue"
                    : "issues"}
                </span>
              </div>

              {issues.length === 0 ? (
                <div className="empty-state">
                  <h3>No issues reported</h3>

                  <p>
                    Create an issue to start tracking it.
                  </p>

                  <button
                    className="create-issue-button"
                    onClick={() =>
                      navigate("/issues/create")
                    }
                  >
                    Create Issue
                  </button>
                </div>
              ) : (
                <div className="issues-list">
                  {issues.map((issue) => (
                    <div
                      className="issue-card"
                      key={issue.id}
                    >
                      <div className="issue-card-main">
                        <div className="issue-title-row">
                          <div>
                            <span className="issue-id">
                              #{issue.id}
                            </span>

                            <h3>{issue.title}</h3>
                          </div>

                          <span
                            className={getPriorityClass(
                              issue.priority
                            )}
                          >
                            {issue.priority}
                          </span>
                        </div>

                        <p className="issue-description">
                          {issue.description}
                        </p>

                        <div className="issue-meta">
                          <span
                            className={getStatusClass(
                              issue.status
                            )}
                          >
                            {issue.status.replace(
                              "_",
                              " "
                            )}
                          </span>

                          <span>
                            Created{" "}
                            {new Date(
                              issue.createdAt
                            ).toLocaleDateString()}
                          </span>
                        </div>
                      </div>

                      <button
                        className="view-button"
                        onClick={() =>
                          navigate(
                            `/issues/${issue.id}`
                          )
                        }
                      >
                        View
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </section>
          </>
        )}
      </main>
    </div>
  );
}

export default Dashboard;
