# import nltk
# from nltk.corpus import cmudict

# # Download the CMU Pronouncing Dictionary if not already downloaded
# nltk.download('cmudict')

# # Load the CMU Pronouncing Dictionary
# cmu_dict = cmudict.dict()

# def get_phonetic_pronunciation(word):
#     word = word.lower()  # Convert the word to lowercase
#     if word in cmu_dict:
#         return cmu_dict[word]
#     else:
#         return None

# # Example usage:
# word = "hello"
# phonetic_pronunciation = get_phonetic_pronunciation(word)
# if phonetic_pronunciation:
#     print(f"The phonetic pronunciation of '{word}' is: {phonetic_pronunciation}")
# else:
#     print(f"No phonetic pronunciation found for '{word}'.")




# import sounddevice as sd
# import numpy as np
# import speech_recognition as sr
# import nltk
# from nltk.corpus import cmudict

# # Download the CMU Pronouncing Dictionary if not already downloaded
# nltk.download('cmudict')

# # Load the CMU Pronouncing Dictionary
# cmu_dict = cmudict.dict()

# def record_voice():
#     sample_rate = 44100  # Sample rate
#     duration = 5  # Duration in seconds

#     print("Recording...")
#     audio_data = sd.rec(int(duration * sample_rate), samplerate=sample_rate, channels=1, dtype='int16')
#     sd.wait()

#     return audio_data.flatten(), sample_rate

# def recognize_speech(audio_data, sample_rate):
#     recognizer = sr.Recognizer()
    
#     audio_data = np.array(audio_data, dtype=np.int16)
#     audio_data = sr.AudioData(audio_data.tobytes(), sample_rate=sample_rate, sample_width=2)  # Convert to AudioData
    
#     try:
#         text = recognizer.recognize_google(audio_data)
#         return text
#     except sr.UnknownValueError:
#         return "Google Speech Recognition could not understand the audio"
#     except sr.RequestError as e:
#         return f"Could not request results from Google Speech Recognition service; {e}"

# def get_phonetic_pronunciation(word):
#     word = word.lower()  # Convert the word to lowercase
#     if word in cmu_dict:
#         return cmu_dict[word]
#     else:
#         return None

# # Record voice
# audio_data, sample_rate = record_voice()

# # Recognize speech
# recorded_text = recognize_speech(audio_data, sample_rate)
# print("You said:", recorded_text)

# # Check phonetic similarity
# def compare_phonetic_similarity(text1, text2):
#     phonetic1 = get_phonetic_pronunciation(text1)
#     phonetic2 = get_phonetic_pronunciation(text2)
#     if phonetic1 and phonetic2:
#         return phonetic1 == phonetic2
#     else:
#         return False

# text_to_compare = "hello"  # Text to compare with the recorded text
# phonetic_similarity = compare_phonetic_similarity(text_to_compare, recorded_text)
# if phonetic_similarity:
#     print("The phonetic representations match.")
# else:
#     print("The phonetic representations do not match or could not be found.")








# import sounddevice as sd
# import numpy as np
# import speech_recognition as sr
# import nltk
# from nltk.corpus import cmudict
# import pyttsx3

# # Download the CMU Pronouncing Dictionary if not already downloaded
# nltk.download('cmudict')

# # Load the CMU Pronouncing Dictionary
# cmu_dict = cmudict.dict()

# def record_voice():
#     sample_rate = 44100  # Sample rate
#     duration = 5  # Duration in seconds

#     print("Recording...")
#     audio_data = sd.rec(int(duration * sample_rate), samplerate=sample_rate, channels=1, dtype='int16')
#     sd.wait()

#     return audio_data.flatten(), sample_rate

# def recognize_speech(audio_data, sample_rate):
#     recognizer = sr.Recognizer()
    
#     audio_data = np.array(audio_data, dtype=np.int16)
#     audio_data = sr.AudioData(audio_data.tobytes(), sample_rate=sample_rate, sample_width=2)  # Convert to AudioData
    
#     try:
#         text = recognizer.recognize_google(audio_data)
#         return text
#     except sr.UnknownValueError:
#         return "Google Speech Recognition could not understand the audio"
#     except sr.RequestError as e:
#         return f"Could not request results from Google Speech Recognition service; {e}"

# def get_phonetic_pronunciation(word):
#     word = word.lower()  # Convert the word to lowercase
#     if word in cmu_dict:
#         return cmu_dict[word]
#     else:
#         return None

# # Record voice
# audio_data, sample_rate = record_voice()

# # Recognize speech
# recorded_text = recognize_speech(audio_data, sample_rate)
# print("You said:", recorded_text)

# # Check phonetic similarity
# def compare_phonetic_similarity(text1, text2):
#     phonetic1 = get_phonetic_pronunciation(text1)
#     phonetic2 = get_phonetic_pronunciation(text2)
#     if phonetic1 and phonetic2:
#         return phonetic1 == phonetic2
#     else:
#         return False

# text_to_compare = "hello"  # Text to compare with the recorded text
# phonetic_similarity = compare_phonetic_similarity(text_to_compare, recorded_text)
# if phonetic_similarity:
#     print("The phonetic representations match.")
# else:
#     print("The phonetic representations do not match or could not be found.")
#     # Get the correct pronunciation
#     correct_pronunciation = get_phonetic_pronunciation(text_to_compare)
#     if correct_pronunciation:
#         # Convert the correct pronunciation to voice output
#         engine = pyttsx3.init(driverName='sapi5')  # Using 'sapi5' driver
#         engine.say('The correct pronunciation is:')
#         engine.say(' '.join(correct_pronunciation[0]))
#         engine.runAndWait()
#     else:
#         print("No correct pronunciation found for the given text.")


import sounddevice as sd
import numpy as np
import speech_recognition as sr
import nltk
from nltk.corpus import cmudict
import pyttsx3

# Download the CMU Pronouncing Dictionary if not already downloaded
nltk.download('cmudict')

# Load the CMU Pronouncing Dictionary
cmu_dict = cmudict.dict()

def record_voice():
    sample_rate = 44100  # Sample rate
    duration = 5  # Duration in seconds

    print("Recording...")
    audio_data = sd.rec(int(duration * sample_rate), samplerate=sample_rate, channels=1, dtype='int16')
    sd.wait()

    return audio_data.flatten(), sample_rate

def recognize_speech(audio_data, sample_rate):
    recognizer = sr.Recognizer()
    
    audio_data = np.array(audio_data, dtype=np.int16)
    audio_data = sr.AudioData(audio_data.tobytes(), sample_rate=sample_rate, sample_width=2)  # Convert to AudioData
    
    try:
        text = recognizer.recognize_google(audio_data)
        return text
    except sr.UnknownValueError:
        return "Google Speech Recognition could not understand the audio"
    except sr.RequestError as e:
        return f"Could not request results from Google Speech Recognition service; {e}"

def get_phonetic_pronunciation(word):
    word = word.lower()  # Convert the word to lowercase
    if word in cmu_dict:
        return cmu_dict[word]
    else:
        return None

# # Record voice
# audio_data, sample_rate = record_voice()

# # Recognize speech
# recorded_text = recognize_speech(audio_data, sample_rate)
# print("You said:", recorded_text)

# Check phonetic similarity
def compare_phonetic_similarity(text1, text2):
    phonetic1 = get_phonetic_pronunciation(text1)
    phonetic2 = get_phonetic_pronunciation(text2)
    if phonetic1 and phonetic2:
        return phonetic1 == phonetic2
    else:
        return False
