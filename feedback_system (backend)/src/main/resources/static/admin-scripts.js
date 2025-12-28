// admin_scripts.js
document.addEventListener('DOMContentLoaded', function() {
  const modal = document.getElementById('feedback-modal');
  const modalClose = document.getElementById('modal-close');
  const saveBtn = document.getElementById('save-feedback');
  const cancelBtn = document.getElementById('cancel-feedback');

  // helpers
  function openModal(data) {
    modal.style.display = 'block';
    modal.setAttribute('data-feedback-id', data.id);
    document.getElementById('modal-category').innerText = data.category;
    document.getElementById('modal-title').innerText = data.title;
    document.getElementById('modal-description').innerText = data.description;
    document.getElementById('status-update').value = data.status || 'Pending';
    document.getElementById('reply-text').value = data.reply || '';
    modal.setAttribute('aria-hidden', 'false');
  }

  function closeModal() {
    modal.style.display = 'none';
    modal.removeAttribute('data-feedback-id');
    modal.setAttribute('aria-hidden', 'true');
  }

  // attach click handler to each row
  document.querySelectorAll('.feedback-row').forEach(row => {
    row.addEventListener('click', function() {
      const data = {
        id: row.getAttribute('data-id'),
        category: row.getAttribute('data-category'),
        title: row.getAttribute('data-title'),
        description: row.getAttribute('data-description'),
        status: row.getAttribute('data-status'),
        votes: row.getAttribute('data-votes')
      };
      openModal(data);
    });
  });

  // close modal
  modalClose.addEventListener('click', closeModal);
  cancelBtn.addEventListener('click', closeModal);
  window.addEventListener('click', function(e) {
    if (e.target === modal) closeModal();
  });

  // save button -> send AJAX
  saveBtn.addEventListener('click', function() {
    const feedbackId = modal.getAttribute('data-feedback-id');
    if (!feedbackId) return alert('Missing feedback id.');

    const status = document.getElementById('status-update').value;
    const reply = document.getElementById('reply-text').value;

    const form = new FormData();
    form.append('feedback_id', feedbackId);
    form.append('status', status);
    form.append('reply', reply);

    fetch('update-feedback.php', {
      method: 'POST',
      body: form,
      credentials: 'same-origin'
    })
    .then(resp => resp.json())
    .then(json => {
      if (!json.success) {
        alert('Error: ' + (json.message || 'Unknown error'));
        return;
      }

      // Update counts in the cards
      if (json.counts) {
        document.getElementById('total-feedback').innerText = json.counts.total;
        document.getElementById('resolved-feedback').innerText = json.counts.resolved;
        document.getElementById('reviewed-feedback').innerText = json.counts.reviewed;
      }

      // Update the table row status and votes
      const row = document.querySelector(`.feedback-row[data-id="${feedbackId}"]`);
      if (row) {
        row.querySelector('.status-cell').innerText = json.updated_row.status;
        row.setAttribute('data-status', json.updated_row.status);
        const votesCell = row.querySelector('.votes-cell');
        if (votesCell) votesCell.innerText = json.updated_row.votes;
        row.setAttribute('data-votes', json.updated_row.votes);
      }

      closeModal();
      // optionally show a small toast
      alert('Feedback updated successfully.');
    })
    .catch(err => {
      console.error(err);
      alert('Request failed.');
    });
  });
});
