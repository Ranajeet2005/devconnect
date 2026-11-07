
import { useState } from "react";
import "./AiTaskGenerator.css";

function AiTaskGenerator() {
  const [description, setDescription] = useState("");
  const [tasks, setTasks] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleGenerate = async () => {
    if (!description.trim()) {
      setError("Please enter your project description.");
      return;
    }

    const token = localStorage.getItem("devconnect_token");

    if (!token) {
      setError("Please log in again.");
      return;
    }

    setLoading(true);
    setError("");
    setTasks("");

    try {
      const response = await fetch(
        "http://localhost:8080/api/ai/generate-tasks",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            description: description.trim(),
          }),
        }
      );

      const result = await response.text();

      if (!response.ok) {
        throw new Error(result || `Request failed: ${response.status}`);
      }

      setTasks(result);
    } catch (err) {
      setError(err.message || "Unable to generate tasks.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="task-generator">
      <div className="task-generator-header">
        <span>POWERED BY OPENAI</span>
        <h2>AI Task Generator</h2>
        <p>
          Convert your software project idea into actionable
          development tasks.
        </p>
      </div>

      <label htmlFor="project-description">
        Describe your project
      </label>

      <textarea
        id="project-description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        placeholder="Example: Build a task management application using Java, Spring Boot and PostgreSQL..."
        maxLength={2000}
      />

      <small>{description.length}/2000 characters</small>

      <button
        onClick={handleGenerate}
        disabled={loading}
      >
        {loading ? "Generating Tasks..." : "✨ Generate Tasks"}
      </button>

      {error && <p className="task-error">{error}</p>}

      {tasks && (
        <div className="generated-tasks">
          <h3>Generated Development Tasks</h3>
          <pre>{tasks}</pre>
        </div>
      )}
    </section>
  );
}

export default AiTaskGenerator;
