import { useState } from "react";
import { generateProjectPlan } from "./api";
import "./AiCopilot.css";

function AiCopilot() {
    const [idea, setIdea] = useState("");
    const [plan, setPlan] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleGenerate = async () => {
        if (!idea.trim()) {
            setError("Please describe your project idea.");
            return;
        }

        setLoading(true);
        setError("");
        setPlan("");

        try {
            const result = await generateProjectPlan(idea.trim());
            setPlan(result.plan);
        } catch (err) {
            setError(err.message || "Unable to generate your project plan.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="ai-copilot-page">
            <div className="ai-copilot-header">
                <span className="ai-label">POWERED BY OPENAI</span>
                <h1>AI Project Copilot</h1>
                <p>
                    Turn your project idea into a practical development roadmap.
                </p>
            </div>

            <div className="ai-copilot-card">
                <label htmlFor="projectIdea">Describe your project idea</label>

                <textarea
                    id="projectIdea"
                    value={idea}
                    onChange={(e) => setIdea(e.target.value)}
                    placeholder="Example: Build an AI-powered job portal using React, Spring Boot and PostgreSQL..."
                    maxLength={2000}
                    rows={7}
                />

                <div className="ai-input-footer">
                    <span>{idea.length}/2000 characters</span>

                    <button
                        onClick={handleGenerate}
                        disabled={loading || !idea.trim()}
                    >
                        {loading ? "Generating..." : "✨ Generate Project Plan"}
                    </button>
                </div>

                {loading && (
                    <div className="ai-loading">
                        <div className="ai-spinner"></div>
                        <p>AI is preparing your development roadmap...</p>
                    </div>
                )}

                {error && (
                    <div className="ai-error">
                        {error}
                    </div>
                )}
            </div>

            {plan && (
                <div className="ai-result-card">
                    <div className="ai-result-header">
                        <h2>Your AI-generated project plan</h2>
                        <button
                            onClick={() =>
                                navigator.clipboard.writeText(plan)
                            }
                        >
                            Copy Plan
                        </button>
                    </div>

                    <pre className="ai-plan-content">{plan}</pre>
                </div>
            )}
        </div>
    );
}

export default AiCopilot;