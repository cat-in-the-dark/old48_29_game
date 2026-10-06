// Web Audio replacement for libgdx Sound/Music.
const SOUND_FILES = {
  explosion: 'assets/sound/explosion.wav',
  smallExplosion: 'assets/sound/small_explosion.wav',
  oh1: 'assets/sound/oh1.mp3',
  oh2: 'assets/sound/oh2.mp3',
  kamazExplosion: 'assets/sound/kamaz_destroy.mp3',
  music: 'assets/sound/bgm.mp3',
};

export class AudioEngine {
  constructor() {
    this.ctx = null;
    this.buffers = {};
    this.musicGain = null;
    this.musicSource = null;
  }

  async load() {
    try {
      this.ctx = new (window.AudioContext || window.webkitAudioContext)();
    } catch (e) {
      console.warn('Web Audio unavailable', e);
      return;
    }
    for (const [name, path] of Object.entries(SOUND_FILES)) {
      try {
        const res = await fetch(path);
        const buf = await res.arrayBuffer();
        this.buffers[name] = await this.ctx.decodeAudioData(buf);
      } catch (e) {
        console.warn('failed to load sound', path, e);
      }
    }
  }

  resume() {
    if (this.ctx && this.ctx.state === 'suspended') this.ctx.resume();
  }

  play(name, volume = 1.0) {
    if (!this.ctx || !this.buffers[name]) return;
    const src = this.ctx.createBufferSource();
    src.buffer = this.buffers[name];
    const gain = this.ctx.createGain();
    gain.gain.value = volume;
    src.connect(gain);
    gain.connect(this.ctx.destination);
    src.start();
  }

  playMusic(volume = 0.7) {
    if (!this.ctx || !this.buffers.music) return;
    if (this.musicSource) return;
    this.musicGain = this.ctx.createGain();
    this.musicGain.gain.value = volume;
    this.musicGain.connect(this.ctx.destination);
    this.musicSource = this.ctx.createBufferSource();
    this.musicSource.buffer = this.buffers.music;
    this.musicSource.loop = true;
    this.musicSource.connect(this.musicGain);
    this.musicSource.start();
  }
}

export const audio = new AudioEngine();
