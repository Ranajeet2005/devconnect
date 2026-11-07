const API_BASE_URL = "http://localhost:8080";

export async function registerUser(userData) {
  return sendRequest("/api/auth/register", userData);
}

export async function loginUser(userData) {
  return sendRequest("/api/auth/login", userData);
}

async function sendRequest(endpoint, data) {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  const result = await response.json().catch(() => null);

  if (!response.ok) {
    throw new Error(
      result?.message || result?.error || `Request failed: ${response.status}`
    );
  }

  return result;
}
export async function generateProjectPlan(idea) {
    const token = localStorage.getItem("devconnect_token");

    const response = await fetch(
        "http://localhost:8080/api/ai/project-plan",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ idea })
        }
    );

    const result = await response.json().catch(() => null);

    if (!response.ok) {
        throw new Error(
            result?.message || result?.error || "Failed to generate project plan"
        );
    }

    return result;
}