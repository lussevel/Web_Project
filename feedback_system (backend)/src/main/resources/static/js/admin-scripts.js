/* Created by: Elcy Moyar D. */
/* This Admin Feedback script manages the modal functionality for viewing and updating feedback.
It allows admins to open a modal by clicking a feedback row, review details, update status,
and send replies to the backend via fetch. The script also updates the table and analytics
counters dynamically without refreshing the page. */


document.addEventListener("DOMContentLoaded", () => {
  console.log("ADMIN SCRIPT LOADED");

  const modal = document.getElementById("feedback-modal");
  const saveBtn = document.getElementById("save-feedback");
  const cancelBtn = document.getElementById("cancel-feedback");
  const closeBtn = document.getElementById("modal-close");

  function openModal(row) {
    modal.style.display = "block";
    modal.dataset.id = row.dataset.id;

    document.getElementById("modal-category").innerText = row.dataset.category;
    document.getElementById("modal-title").innerText = row.dataset.title;
    document.getElementById("modal-description").innerText = row.dataset.description;
    document.getElementById("status-update").value = row.dataset.status;
    document.getElementById("reply-text").value = "";
  }

  function closeModal() {
    modal.style.display = "none";
  }

  document.querySelectorAll(".feedback-row").forEach(row => {
    row.addEventListener("click", () => openModal(row));
  });

  cancelBtn.onclick = closeModal;
  closeBtn.onclick = closeModal;

  saveBtn.onclick = () => {
    const payload = {
      feedbackId: modal.dataset.id,
      status: document.getElementById("status-update").value,
      reply: document.getElementById("reply-text").value
    };

    console.log("SENDING:", payload);

    fetch("/admin/feedback/update", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    })
      .then(res => res.json())
      .then(json => {
        console.log("RESPONSE:", json);

        if (!json.success) {
          alert(json.message || "Update failed");
          return;
        }

        const row = document.querySelector(`.feedback-row[data-id="${payload.feedbackId}"]`);
        row.querySelector(".status-cell").innerText = payload.status;
        row.dataset.status = payload.status;

        document.getElementById("total-feedback").innerText = json.counts.total;
        document.getElementById("resolved-feedback").innerText = json.counts.resolved;
        document.getElementById("reviewed-feedback").innerText = json.counts.reviewed;

        alert("Saved successfully");
        closeModal();
      })
      .catch(err => {
        console.error(err);
        alert("Request failed");
      });
  };
});
