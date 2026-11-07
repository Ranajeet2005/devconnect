
import { useState } from "react";
import Auth from "./Auth";
import AiCopilot from "./AiCopilot";
import AiTaskGenerator from "./AiTaskGenerator";
import "./App.css";

function App() {
  const [active, setActive] = useState("Dashboard");

  const [token, setToken] = useState(
    () => localStorage.getItem("devconnect_token")
  );

  const menu = [
    "Dashboard",
    "Projects",
    "Tasks",
    "Team Members",
    "AI Copilot",
    "AI Task Generator",
    "Settings",
  ];

  const handleLogout = () => {
    localStorage.removeItem("devconnect_token");
    setToken(null);
    setActive("Dashboard");
  };

  const projects = [
    {
      name: "Developer Portfolio",
      description: "Personal portfolio website",
      tech: "React · Spring Boot",
      progress: 75,
      status: "In Progress",
    },
    {
      name: "AI Research Assistant",
      description: "AI-powered research platform",
      tech: "Python · FastAPI",
      progress: 45,
      status: "In Progress",
    },
    {
      name: "Task Management API",
      description: "REST API for task management",
      tech: "Java · PostgreSQL",
      progress: 90,
      status: "Almost Done",
    },
  ];

  // Authentication
  if (!token) {
    return <Auth onAuthenticated={setToken} />;
  }

  return (
    <div className="app">

      {/* Sidebar */}
      <aside className="sidebar">

        <h2 className="logo">
          DevConnect<span>.</span>
        </h2>

        <p className="menu-label">WORKSPACE</p>

        <nav>
          {menu.map((item) => (
            <button
              key={item}
              className={
                active === item
                  ? "nav-item active"
                  : "nav-item"
              }
              onClick={() => setActive(item)}
            >
              {item === "AI Copilot" && "🤖 "}
              {item === "AI Task Generator" && "✨ "}
              {item}
            </button>
          ))}
        </nav>

        {/* User Profile */}
        <div className="profile">
          <div className="avatar">R</div>

          <div>
            <strong>Ranajeet</strong>
            <small>Developer</small>

            <button
              className="logout-btn"
              onClick={handleLogout}
            >
              Logout
            </button>
          </div>
        </div>

      </aside>

      {/* Main Content */}
      <main className="main">

        {/* Top Navigation */}
        <header className="topbar">

          <div>
            <p className="breadcrumb">
              Workspace / {active}
            </p>

            <h1>{active}</h1>
          </div>

          {active !== "AI Copilot" &&
           active !== "AI Task Generator" && (
            <button className="create-btn">
              + Create Project
            </button>
          )}

        </header>

        {/* Dashboard */}
        {active === "Dashboard" && (
          <>
            <section className="welcome">
              <div>
                <h2>Welcome back, Ranajeet! 👋</h2>
                <p>
                  Manage your projects and collaborate with your team.
                </p>
              </div>
            </section>

            {/* Statistics */}
            <section className="stats">

              <div className="stat-card">
                <span>Total Projects</span>
                <h2>08</h2>
                <small>Across your workspace</small>
              </div>

              <div className="stat-card">
                <span>Total Tasks</span>
                <h2>24</h2>
                <small>Track your progress</small>
              </div>

              <div className="stat-card">
                <span>Team Members</span>
                <h2>12</h2>
                <small>Collaborators</small>
              </div>

              <div className="stat-card">
                <span>Completed</span>
                <h2>16</h2>
                <small>Tasks completed</small>
              </div>

            </section>

            {/* Recent Projects */}
            <section className="projects-section">

              <div className="section-heading">
                <div>
                  <h2>Recent Projects</h2>
                  <p>Your latest workspace activity</p>
                </div>

                <button
                  onClick={() => setActive("Projects")}
                >
                  View All
                </button>
              </div>

              <div className="project-grid">

                {projects.map((project) => (
                  <article
                    className="project-card"
                    key={project.name}
                  >

                    <div className="project-top">
                      <div className="project-icon">📁</div>

                      <span className="status">
                        {project.status}
                      </span>
                    </div>

                    <h3>{project.name}</h3>

                    <p>{project.description}</p>

                    <small className="tech">
                      {project.tech}
                    </small>

                    <div className="progress-heading">
                      <span>Progress</span>
                      <strong>
                        {project.progress}%
                      </strong>
                    </div>

                    <div className="progress-bar">
                      <div
                        style={{
                          width: `${project.progress}%`,
                        }}
                      />
                    </div>

                  </article>
                ))}

              </div>
            </section>
          </>
        )}

        {/* Projects */}
        {active === "Projects" && (
          <section className="projects-section">

            <h2>All Projects</h2>

            <div className="project-grid">

              {projects.map((project) => (
                <article
                  className="project-card"
                  key={project.name}
                >
                  <h3>{project.name}</h3>

                  <p>{project.description}</p>

                  <small className="tech">
                    {project.tech}
                  </small>

                  <p>{project.progress}% completed</p>
                </article>
              ))}

            </div>
          </section>
        )}

        {/* AI Project Copilot */}
        {active === "AI Copilot" && (
          <AiCopilot />
        )}

        {/* AI Task Generator */}
        {active === "AI Task Generator" && (
          <AiTaskGenerator />
        )}

        {/* Other Sections */}
        {active !== "Dashboard" &&
         active !== "Projects" &&
         active !== "AI Copilot" &&
         active !== "AI Task Generator" && (

          <section className="welcome">
            <div>
              <h2>{active}</h2>

              <p>
                This section will be implemented
                in the next development step.
              </p>
            </div>
          </section>

        )}

      </main>
    </div>
  );
}

export default App;
