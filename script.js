const form   = document.getElementById("imageForm");
const output = document.getElementById("output");

// Resize image to satisfy Stability‑AI limits (320 – 1536 px)
async function resizeImageIfNeeded(file) {
  const img = new Image();
  const reader = new FileReader();

  const dataUrl = await new Promise((resolve, reject) => {
    reader.onload = e => resolve(e.target.result);
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
  img.src = dataUrl;

  await new Promise(res => (img.onload = res));

  const minDim = 320;
  const maxDim = 1536;
  let { width, height } = img;

  if (
    (width >= minDim && width <= maxDim && height >= minDim && height <= maxDim)
  ) {
    return file; // Already valid
  }

  // Resize proportionally
  let scale = 1;
  if (width > maxDim || height > maxDim) {
    scale = Math.min(maxDim / width, maxDim / height);
  }
  width = Math.floor(width * scale);
  height = Math.floor(height * scale);

  if (width < minDim || height < minDim) {
    const upscale = Math.max(minDim / width, minDim / height);
    width = Math.floor(width * upscale);
    height = Math.floor(height * upscale);
  }

  const canvas = document.createElement("canvas");
  canvas.width = width;
  canvas.height = height;
  const ctx = canvas.getContext("2d");
  ctx.drawImage(img, 0, 0, width, height);

  return new Promise(resolve => {
    canvas.toBlob(blob => {
      resolve(new File([blob], file.name.replace(/\.[^.]+$/, ".png"), { type: "image/png" }));
    }, "image/png");
  });
}

form.addEventListener("submit", async event => {
  event.preventDefault();
  output.innerHTML = "<p style='color:#ffcc00;'>Generating… Please wait.</p>";

  const prompt = document.getElementById("prompt").value.trim();
  const style  = document.getElementById("style").value || "general";
  let   imageFile = document.getElementById("image").files[0] || null;

  try {
    let response;

    if (imageFile) {
      imageFile = await resizeImageIfNeeded(imageFile);

      const formData = new FormData();
      formData.append("prompt", prompt);
      formData.append("image",  imageFile);

      response = await fetch("http://localhost:8080/api/v1/generate", {
        method: "POST",
        body: formData
      });
    } else {
      response = await fetch("http://localhost:8080/api/v1/generate-from-text", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ prompt, style })
      });
    }

    if (!response.ok) {
      throw new Error("Backend error " + response.status);
    }

    const blob = await response.blob();
    const imgUrl = URL.createObjectURL(blob);
    output.innerHTML = `<img src="${imgUrl}" alt="Generated image" />`;
  } catch (err) {
    output.innerHTML = `<p style='color:red;'>Error: ${err.message}</p>`;
  }
});
