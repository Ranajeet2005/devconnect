
import { useState } from "react";
import { loginUser, registerUser } from "./api";
import "./Auth.css";

function Auth({ onAuthenticated }) {
  const [mode, setMode] = useState("login");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const userData = {
        email: form.email.trim(),
        password: form.password,
      };

      const result =
        mode === "register"
          ? await registerUser({ ...userData, name: form.name.trim() })
          : await loginUser(userData);

      if (!result?.token) {
        throw new Error("Authentication response did not contain a token.");
      }

      localStorage.setItem("devconnect_token", result.token);
      onAuthenticated(result.token);
    } catch (err) {
      setError(err.message || "Authentication failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const changeMode = (nextMode) => {
    setMode(nextMode);
    setError("");
  };

  return (
    <div className="auth-page">
      <div className="auth-brand">
        <h1>DevConnect<span>.</span></h1>
        <p>Build projects. Connect developers. Create together.</p>
      </div>

      <div className="auth-card">
        <div className="auth-tabs">
          <button
            type="button"
            className={mode === "login" ? "selected" : ""}
            onClick={() => changeMode("login")}
          >
            Login
          </button>

          <button
            type="button"
            className={mode === "register" ? "selected" : ""}
            onClick={() => changeMode("register")}
          >
            Register
          </button>
        </div>

        <h2>{mode === "login" ? "Welcome back!" : "Create your account"}</h2>
        <p className="auth-subtitle">
          {mode === "login"
            ? "Sign in to continue to your workspace."
            : "Join developers and start collaborating."}
        </p>

        {error && <div className="auth-error">{error}</div>}

        <form onSubmit={handleSubmit}>
          {mode === "register" && (
            <label>
              Full Name
              <input
                name="name"
                type="text"
                placeholder="Enter your name"
                value={form.name}
                onChange={handleChange}
                minLength={2}
                maxLength={50}
                required
              />
            </label>
          )}

          <label>
            Email Address
            <input
              name="email"
              type="email"
              placeholder="you@example.com"
              value={form.email}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Password
            <div className="password-field">
              <input
                name="password"
                type={showPassword ? "text" : "password"}
                placeholder="Minimum 8 characters"
                value={form.password}
                onChange={handleChange}
                minLength={8}
                required
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? "Hide" : "Show"}
              </button>
            </div>
          </label>

          <button className="auth-submit" type="submit" disabled={loading}>
            {loading
              ? "Please wait..."
              : mode === "login"
                ? "Sign In"
                : "Create Account"}
          </button>
        </form>

        <p className="auth-switch">
          {mode === "login"
            ? "Don't have an account?"
            : "Already have an account?"}
          <button
            type="button"
            onClick={() => changeMode(mode === "login" ? "register" : "login")}
          >
            {mode === "login" ? "Register" : "Login"}
          </button>
        </p>
      </div>
    </div>
  );
}

export default Auth;
